//
//  InterfaceController.swift
//  LocationWatch WatchKit Extension
//
//  Created by Zach Zeleznick on 3/11/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import WatchKit
import Foundation
import WatchConnectivity


class InterfaceController: WKInterfaceController, WCSessionDelegate {

        
    @IBOutlet var connectionLabel: WKInterfaceLabel!
    
    private var session: WCSession? = WCSession.isSupported() ? WCSession.defaultSession() : nil
    
    var connected = false {
        willSet(value) {
            connectionLabel.setText("\(value)")
        }
    }

    override init() {
        super.init()
        session?.delegate = self
        session?.activateSession()
        checkSession()
        // session:activationDidCompleteWithState
    }
    
    func checkSession() {
        if session != nil {
            connected = true
        }
        else {
            connected = false
        }
    }
    
    func reestablishConnection() {
        session?.delegate = self
        session?.activateSession()
        checkSession()
    }
    
    @IBAction func resetButtonPressed() {
        reestablishConnection()
    }
    
    override func awakeWithContext(context: AnyObject?) {
        super.awakeWithContext(context)
        // Configure interface objects here.
    }
    
    override func willActivate() {
        // This method is called when watch view controller is about to be visible to user
        super.willActivate()
        print("Watch Did Activate")
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
