//
//  ViewController.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/11/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//
import UIKit

import MapKit
import WatchConnectivity

import Alamofire
import AlamofireImage

class ViewController: UIViewController, UITextFieldDelegate, CLLocationManagerDelegate, WCSessionDelegate {
    
    // MARK - STORYBOARD CONNECTIONS
    @IBOutlet weak var textField: UITextField!
    
    
    // MARK - LOCAL VARIABLES
    private let limitLength = 5
    var districtName = "CA-33"
    var zipCodeMode = true
    
    var Obama = "50"
    var Romney = "50"
    
    var zipInput = "94720" {
        willSet(newValue) {
            print("Zip Code updating to \(newValue)")
        }
        didSet { // update when set
            print("Zip Code updated from \(oldValue)")
        }
    }
    
    // MARK - LOCAL UTILITIES
    var dataHelper = ElectionDataHelper()
    var geoCoder = CLGeocoder()
    var locationManager: CLLocationManager!
    var location: CLLocation! {
        didSet { // update when set
            print("Found Your Location \(location.coordinate)")
        }
    }
    
    private let session: WCSession? = WCSession.isSupported() ? WCSession.defaultSession() : nil
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        configureWCSession()
    }
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        configureWCSession()
    }
    
    private func configureWCSession() {
        session?.delegate = self;
        session?.activateSession()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Set up escape for textfields
        textField.delegate = self
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: "dismissKeyboard")
        view.addGestureRecognizer(tap)
        // Hide the Back Button on first page
        self.navigationItem.hidesBackButton = true
    }
    
    override func viewDidLayoutSubviews() {
        let border = CALayer()
        let width = CGFloat(2.0)
        border.borderColor = UIColor.lightGrayColor().CGColor
        border.frame = CGRect(x: 0, y: textField.frame.size.height - width, width:  textField.frame.size.width, height: textField.frame.size.height)
        
        border.borderWidth = width
        textField.layer.addSublayer(border)
        textField.layer.masksToBounds = true
        
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        checkCoreLocationPermission()
        
        
    }
    
    func checkCoreLocationPermission() {
        // Note that plist must have the row "NSLocationWhenInUseUsageDescription"
        // this allows us to access location
        
        print("Auth status: +\(CLLocationManager.authorizationStatus())")
        
        if CLLocationManager.authorizationStatus() == .AuthorizedWhenInUse {
            locationManager.startUpdatingLocation()
        }
        else if CLLocationManager.authorizationStatus() == .NotDetermined {
            locationManager.requestWhenInUseAuthorization()
        }
        else if CLLocationManager.authorizationStatus() == .Restricted {
            // should trigger alert
            print("Unauthorized to use location")
        }
    }
    
    // MARK: - CLLocationManagerDelegate
    // Updates the location and the zipcode on screen
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        print("manager invoked")
        location = (locations).last!
        locationManager.stopUpdatingLocation() // save the battery
        geoCoder.reverseGeocodeLocation(location) {
            (placements, myError) -> Void in
            if myError != nil { // handle error
                print(myError)
            }
            else {
                if let placement = placements?.first {
                    self.zipInput = "\(placement.postalCode!)"
                    self.textField.text = self.zipInput
                }
            }
        }
    }

    func notifyBadZipcode(zipcode:String) {
        let alertController = UIAlertController(title: "Represent",
            message: "Zip Code \(zipcode) Not Valid",
            preferredStyle: UIAlertControllerStyle.Alert)
        alertController.addAction(UIAlertAction(title: "OK",
            style: UIAlertActionStyle.Default,handler: nil))
        
        self.presentViewController(alertController, animated: true) { (action) in
            // displays the alert
        }
    }
    
    // the main action receiver
    @IBAction func goButtonPressed(sender: UIButton) {
        print("Go button pressed")
        if textField.text!.characters.count < 5 {
            // Invalid USA Zipcode
            notifyBadZipcode(textField.text!)}
        else {
            locationPipe() { val in
                print("Inside Location Pipe")
                let _ = try! self.getDistrict() {
                    (stateAbbreviation, district)  in
                    print("Received State \(stateAbbreviation), \(district)")
                    let (obama, romney) = self.dataHelper.getObamaRomneyTupleForDistrict(stateAbbreviation, districtNumber: district)
                    print("Obama: \(obama), Romney: \(romney)")
                    self.districtName = "\(stateAbbreviation)-\(district)"
                    self.Obama = "\(obama)"
                    self.Romney = "\(romney)"
                    self.sendMessageToWatch()
                    self.performSegueWithIdentifier("MainToTable", sender: nil)
                }
            }
        }
    }
    
    enum LocationError: ErrorType {
        case NoLocationFound
        case NoDistrictFound(lat:String, lng:String)
    }
    
    // gets the district from your current lat and long
    // then calls a completion handler to deal with the result
    func getDistrict(completion: (String, String) -> () ) throws{
        print("Entering Get District")
        guard location != nil else {
            print("No Location Found")
            throw LocationError.NoLocationFound
        }
        // var out: (String, String) = ("", "")
        let lat = location.coordinate.latitude
        let lng = location.coordinate.longitude
        let _ = SunlightDistrictAPI(latitude: "\(lat)", longitude: "\(lng)") { dict in
                    if let stateAbbreviation = dict["state"],
                        districtNumber = dict["district"] {
                            var district = districtNumber
                            if district.characters.count == 1 {
                                district = "0" + district
                            }
                        print("Found District \(stateAbbreviation)-\(district)")
                        completion(stateAbbreviation, district)
                    }
            }
    }
    
    // Calls the location manager and updates the location and zipcode
    @IBAction func locationButtonPressed(sender: UIButton) {
        print("Location button pressed")
        zipCodeMode = false
        locationManager.startUpdatingLocation()
    }
    
    // Gets either a zipcode or location and
    // then calls the completion function on the result
    func locationPipe(completion: Int -> () ) {
        if location != nil && !zipCodeMode {
            completion(1)
        }
        else {
            let zipcode = textField.text!
            geoCoder.geocodeAddressString(zipcode){ (placemarks, error) -> Void in
                print("Entering Zipcode Conversion")
                if let firstPlacemark = placemarks?[0] {
                    self.location = firstPlacemark.location!
                    completion(2)
                }
                else {
                    print("USED \(zipcode)")
                    print(error)
                    self.notifyBadZipcode(zipcode)
                }
            }
        }
    }
    
    // Sends the election Data to the watch
    func sendMessageToWatch() {
        // send Message to watch
        print("Send Message From Phone Called")
        if ((self.session) != nil) {
            if location != nil {
                let district = self.districtName
                let obama = self.Obama
                let romney = self.Romney
                let dict:[String:String] = ["district": district,
                "obama": obama, "romney": romney]
                let msg = dict as AnyObject
                session!.sendMessage(["a": msg], replyHandler: nil, errorHandler: nil)
            }
            else {
                print("No Location found yet")
            }
        }
        else {
            print("Session is nil")
            self.textField.text = "404"
        }
    }
    
    func getRepImageURL(repID: String) -> String {
        return "https://github.com/unitedstates/images/blob/gh-pages/congress/450x550/\(repID).jpg?raw=true"
    }
    
    func passOnData(dest: TableViewController) throws {
        guard location != nil else {
            print("No Location Found")
            throw LocationError.NoLocationFound
        }
        let lat = "\(self.location.coordinate.latitude)"
        let lng = "\(self.location.coordinate.longitude)"
        let _ = SunlightRepAPI(latitude: lat, longitude: lng) { res in
            if res.count == 3 {
                dest.newText = "\(self.districtName)"
                dest.myLabel?.text = "\(self.districtName)"
                dest.names = []
                dest.repURLs = [NSURL]()
                let paths = dest.tableTasks.indexPathsForVisibleRows
                for (idx, dict) in res.enumerate() {
                    if idx > paths!.count || idx > 2{
                        print("Stopped at \(idx)")
                        break
                    }
                    let input = dict["name"]
                    dest.names.append(input!)
                    let imageURL = self.getRepImageURL(dict["id"]!)
                    // print("Image URL: \(imageURL)")
                    let URL = NSURL(string:imageURL)
                    let cell = dest.tableTasks.cellForRowAtIndexPath(paths![idx]) as! CustomCell
                    cell.repDict = dict
                    cell.twitter?.text = "@\(dict["twitter"]!)"
                    cell.party?.text = dict["party"]
                    cell.email?.text = dict["email"]
                    cell.website?.text = dict["website"]
                    let placeholderImage = UIImage(named: "kitten")!
                    cell.myImage?.af_setImageWithURL(URL!, placeholderImage: placeholderImage, filter: nil, imageTransition: .None, runImageTransitionIfCached: false, completion: self.test )
                }
                // self.navigationController?.pushViewController(dest, animated: true)
                dest.tableTasks?.reloadData()
                print("Reloaded Table View")
            }
            else {
                print("INVALID ZIP CODE")
                self.performSegueWithIdentifier("MainToError", sender: self)
            }
        }
    }
    
    func test(rep: Response<UIImage, NSError>) -> Void {
        // print("Calling the cache")
        // self.cache.tableTasks?.reloadData()
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        if let dest = segue.destinationViewController as? TableViewController {
            print("Going to table controller")
            try! self.passOnData(dest)
        }
        else {
            print("Going somewhere else")
            let other = segue.destinationViewController
            other.navigationController?.setNavigationBarHidden(true, animated: true)
            other.navigationItem.hidesBackButton = true
        }
        
    }
    
    // Calls this function when the tap is recognized.
    func dismissKeyboard() {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        view.endEditing(true)
    }
    // Textfield Properties
    func textFieldShouldClear(textField: UITextField) -> Bool {
        // called when clear button pressed.
        return true
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        // called when 'return' key pressed.
        textField.resignFirstResponder()
        return true
    }
    func textFieldDidBeginEditing(textField: UITextField) {
        zipCodeMode = true
    }
    
    // Prevents non-numeric input or zipcodes longer than 5 characters
    func textField(textField: UITextField, shouldChangeCharactersInRange range: NSRange, replacementString string: String) -> Bool {
        let invalidCharacters = NSCharacterSet(charactersInString: "0123456789").invertedSet
        if let _ = string.rangeOfCharacterFromSet(invalidCharacters, options: [], range:Range<String.Index>(start: string.startIndex, end: string.endIndex)) {
            return false
        }
        else {
            guard let text = textField.text else { return true }
            let newLength = text.characters.count + string.characters.count - range.length
            return newLength <= limitLength
        }
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}

