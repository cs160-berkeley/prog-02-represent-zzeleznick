//
//  SunriseDistrictAPI.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import Alamofire

class SunlightDistrictAPI {
    /* Due to ambiguity in reading a zipcode
    (i.e. some return 0 or 4 instead of 3),
    A lat-long tuple will be used exclusively
    A zipcode must be geocoded to produce a lat-long
    and thus a CoreLocation lat-long will be more accurate */
    
    var data: [ [String:String] ]! //Array of dictionary
    var complete: Bool = false
    init( latitude:String, longitude:String, completion: [String:String] -> () ) {
        data = [ [String:String] ]()
        let URL = "http://congress.api.sunlightfoundation.com/districts/locate?latitude=\(latitude)&longitude=\(longitude)&apikey=02f5124ba8c74a6db133f9096d584b5c"
        // print(URL)
        Alamofire.request(.GET, URL).validate().responseJSON { (response) in
            switch response.result {
            case .Success:
                if let value = response.result.value {
                    let json = JSON(value)
                    // let bob = json["result"].arrayValue
                    // print(bob)
                    // print("JSON: \(json)")
                    // completion(json)
                    self.firstCompletion(json, closer: completion)
                    // print(self.data)
                }
            case .Failure(let error):
                print(error)
            }
        }
    }
    
    enum APIError: ErrorType {
        case NoDistrictFound
    }
    
    func firstCompletion(data: JSON, closer: [String:String] -> () ) {
        // should just return state and district
        let count = data["count"].floatValue
        if count != 1 {
            print("WARN: Found \(count) results")
        }
        var dictArray = [ [String:String] ]()
        for item in data["results"].arrayValue {
            let stateAbbreviation = item["state"].stringValue
            let districtNumber = item["district"].stringValue
            let dict = ["state": stateAbbreviation, "district": districtNumber]
            dictArray.append(dict)
        }
        /* guard let out = dictArray.first else {
            throw APIError.NoDistrictFound
        } */
        if let out = dictArray.first {
            closer(out)
        }
    }
}

