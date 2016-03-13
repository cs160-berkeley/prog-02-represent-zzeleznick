//
//  TableViewController.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

//
//  TableViewController.swift
//  TableViewControllerDemo
//
//  Created by Zach Zeleznick on 3/5/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import UIKit
import AlamofireImage

class TableViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    
    @IBOutlet weak var myLabel: UILabel!
    @IBOutlet weak var tableTasks: UITableView!
    
    var oldNames = ["First", "Second", "Third"]
    var names: [String]!
    var repURLs: [NSURL]!
    var firstTableArray = [String]()
    var newText: String = "Label"
    var myIndexPath: NSIndexPath!
    var urls = ["https://40.media.tumblr.com/aa1b7656aac5a75592caefab4cae2efc/tumblr_o3jwthhMWJ1v33hszo1_1280.jpg", "https://41.media.tumblr.com/e968013665b914e8fa89e21b27e98740/tumblr_o3e8rzUxrW1v33hszo1_1280.png", "https://41.media.tumblr.com/10284dc27f220100c38f1f51f27904e3/tumblr_o31h0aF53u1v33hszo1_1280.png"]
    
    var result: [ [String:String] ]!
    var commmittees: [ [String:String] ]!
    var bills: [ [String:String] ]!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if names != nil {
            print("Got Names")
        }
        else {
            print("Waiting for names")
        }
        firstTableArray = oldNames
        myLabel.text = newText
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func getRepImageURL(repID: String) -> String {
        return "https://github.com/unitedstates/images/blob/gh-pages/congress/450x550/\(repID)).jpg?raw=true"
    }
    
    // Returning to view
    override func viewWillAppear(animated: Bool) {
        tableTasks.reloadData()
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let idx = indexPath.row
        let cell = tableTasks.dequeueReusableCellWithIdentifier("test", forIndexPath: indexPath) as! CustomCell
        
        if idx < names?.count {
            let item = names?[idx]
            cell.name?.text = item
        }
        else {
            cell.name?.text = "No Represenative Found"
        }
        
        return cell
    }
    
    func tableView(tableView: UITableView, willSelectRowAtIndexPath indexPath: NSIndexPath) -> NSIndexPath? {
        myIndexPath = indexPath
        print("User will tap \(myIndexPath.row)")
        performSegueWithIdentifier("TableToDetail", sender: self)
        return indexPath
    }
    
    func passCommitteeDataOn(dest: DetailViewController, bioID: String) {
        print("Pass Committees Called")
        commmittees = [ [String:String] ]()
        let _ = SunlightCommitteeAPI(bioID: bioID) { response in
            for item in response["results"].arrayValue {
                let cID = item["committee_id"].stringValue
                let name = item["name"].stringValue
                let dict = ["name": name, "committee_id": cID]
                self.commmittees.append(dict)
            }
            print("Received JSON Response for Committees from Sunlight")
            if self.commmittees.count > 0 {
                dest.committeeResult = self.commmittees
                var text = ""
                for dict in self.commmittees {
                    text.appendContentsOf(dict["name"]! + " ")
                }
                dest.committees?.text = text
                print("Got Committees")
            }
            else {
                print("No Committees")
            }
        }
        
    }
    
    func passBillDataOn(dest: DetailViewController, bioID: String) {
        print("Pass Committees Called")
        bills = [ [String:String] ]()
        let _ = SunlightBillAPI(bioID: bioID) { response in
            for item in response["results"].arrayValue {
                let bID = item["bill_id"].stringValue
                let name = item["short_title"].stringValue
                let dict = ["short_title": name, "bill_id": bID]
                self.bills.append(dict)
            }
            print("Received JSON Response for Bills from Sunlight")
            if self.bills.count > 0 {
                var text = ""
                for dict in self.bills {
                    text.appendContentsOf(dict["short_title"]! + " ")
                }
                dest.recentBills?.text = text
                print("Got Bills")
            }
            else {
                print("No Bills")
            }
        }
        
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let dest : DetailViewController = segue.destinationViewController as! DetailViewController
        let cell = tableTasks.cellForRowAtIndexPath(myIndexPath) as! CustomCell
        dest.repDict = cell.repDict
        dest.navigationItem.title = cell.repDict["name"]
        dest.newImage = cell.myImage?.image
        if let id = cell.repDict["id"] {
            passCommitteeDataOn(dest, bioID: id)
            passBillDataOn(dest, bioID: id)
        }
        else {
            print("ID NOT LOADED YET")
        }
        print("Prepare for segue called from tableview")
        dest.imageLoaded = true
    }
    
    
}