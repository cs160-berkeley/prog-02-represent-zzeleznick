//
//  ElectionDataHelper.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import Foundation


class ElectionDataHelper {
    
    var districts: [String: Dictionary<String, AnyObject>]!
    
    init() {
        makeDictionary()
    }
    
    func convertStringToDictionary(text: String) -> [String:AnyObject]? {
        if let data = text.dataUsingEncoding(NSUTF8StringEncoding) {
            do {
                let json = try NSJSONSerialization.JSONObjectWithData(data, options: .MutableContainers) as? [String:AnyObject]
                print("YOOOOO")
                return json
            } catch let error as NSError {
                print("NOOOO")
                print(error.localizedDescription)
            }
        }
        return nil
    }

    func makeDictionary() {
        // Setup function to parse raw election data and
        // store results in district dictionary of dictionaries
        print("Entering Dictionary Call")
        let res = convertStringToDictionary(ElectionDataSource)
        let desired = res!["districts"]!
        let datum = desired as! [AnyObject]
        self.districts = processDictionary(datum)
    }

    func processDictionary(datum: [AnyObject]) -> [String: Dictionary<String, AnyObject> ] {
        // Processes an input array of election data and converts
        // each object to a dictionary keyed by the concatenation
        // of State Abbreviation and district number
        var newDict = [String: Dictionary<String, AnyObject> ]()
        for dict in datum {
            if let name = dict["name"] as? String {
                // print(name)
                let split = name.componentsSeparatedByString("-")
                let firstKey =  split.first! // abbreviation
                var secondKey = split.last! // district
                if secondKey == "AL" {secondKey = "00"}
                var sub = dict as! Dictionary<String, AnyObject>
                sub.updateValue(firstKey, forKey: "abbreviation")
                sub.updateValue(secondKey, forKey: "district")
                let name = firstKey + "-" + secondKey
                newDict.updateValue(sub, forKey: name)
            }
        }
        print("Finished Proccessing Election Data")
        return newDict
    }
    
    func getObamaRomneyTupleForDistrict(abbrev: String, districtNumber: String) -> (String, String) {
        let district = self.districts["\(abbrev)-\(districtNumber)"]
        let obamaValue = "\(district!["Obama 2012"]!)" // was __NSCFNumber
        let romneyValue = "\(district!["Romney 2012"]!)"
        return (obamaValue, romneyValue)
    }
    
    func getDistrictForState(index: Int) -> [ Dictionary<String, AnyObject> ] {
        // index: The index of the state to select
        // returns an array of dictionaries of election data for a state
        let raw = index
        let myState = State(rawValue: raw)
        var myDistricts = [ Dictionary<String, AnyObject> ]()
        if let abbrev = myState?.abbrev {
            print("Fetched state \(abbrev)")
            let limit = myState?.districtsCount
            if limit == 0 {
                if let district = self.districts["\(abbrev)-00"]{
                    myDistricts.append(district)
                }
            }
            else {
                for i in (1...limit!) {
                    var key = "\(i)"
                    if key.characters.count == 1 {
                        key = "0\(key)"
                    }
                    if let district = self.districts["\(abbrev)-\(key)"]{
                        myDistricts.append(district)
                    }
                }
            }
            return myDistricts
        }
        else {
            print("Error with index \(index)")
            return myDistricts
        }
    }
}