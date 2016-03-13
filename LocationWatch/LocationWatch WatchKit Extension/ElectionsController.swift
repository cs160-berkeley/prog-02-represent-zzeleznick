//
//  ElectionsController.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import Foundation
import WatchKit
import WatchConnectivity


class ElectionsController: WKInterfaceController, WCSessionDelegate {
    
    @IBOutlet var romneyVoteLabel: WKInterfaceLabel!
    @IBOutlet var obamaVoteLabel: WKInterfaceLabel!
    @IBOutlet var districtLabel: WKInterfaceLabel!
    
    private let session: WCSession? = WCSession.isSupported() ? WCSession.defaultSession() : nil
    
    override init() {
        super.init()
        session?.delegate = self
        session?.activateSession()
    }
    
    override func awakeWithContext(context: AnyObject?) {
        super.awakeWithContext(context)
        if let dict: [String:String] = context as? [String:String] {
            // ["name": stateName, "number": districtNumber, "obama": obamaValue, "romney": romneyValue]
            let district = dict["district"]
            let obama = dict["obama"]
            let romney = dict["romney"]
            romneyVoteLabel.setText(romney! + "%")
            obamaVoteLabel.setText(obama! + "%")
            districtLabel.setText(district!)
        }
    }
    
    override func willActivate() {
        // This method is called when watch view controller is about to be visible to user
        super.willActivate()
    }
    
    override func didDeactivate() {
        // This method is called when watch view controller is no longer visible
        super.didDeactivate()
    }
    
    func session(session: WCSession, didReceiveMessage message: [String: AnyObject]) {
        // receiving messages from iphone
        
        //Use this to update the UI instantaneously (otherwise, takes a little while)
        dispatch_async(dispatch_get_main_queue()) {
            if let dict = message["a"]! as? [String: String] {
                let obama:String = dict["obama"]!
                let romney:String = dict["romney"]!
                let district:String = dict["district"]!
                let context = ["obama": obama,
                    "romney": romney, "district": district]
                print("Received context \(context)")
                self.pushControllerWithName("ElectionsWVC",
                    context: context)
            }
            else {
                print("Received Crap from phone")
            }
        }
        
    }
}