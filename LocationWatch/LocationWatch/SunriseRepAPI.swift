//
//  SunriseRepAPI.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

//
//  SunlightRepAPI.swift
//  TableViewControllerDemo
//
//  Created by Zach Zeleznick on 3/8/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import Foundation

import Alamofire

class SunlightRepAPI {
    /* Due to ambiguity in reading a zipcode
    (i.e. some return 0 or 4 instead of 3),
    A lat-long tuple will be used exclusively
    A zipcode must be geocoded to produce a lat-long
    and thus a CoreLocation lat-long will be more accurate */
    
    var data: [ [String:String] ]! //Array of dictionary
    var complete: Bool = false
    init( latitude:String, longitude:String, completion : [[String:String]] -> () ) {
        data = [ [String:String] ]()
        let URL = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=\(latitude)&longitude=\(longitude)&apikey=02f5124ba8c74a6db133f9096d584b5c"
        // print(URL)
        Alamofire.request(.GET, URL).validate().responseJSON { (response) in
            switch response.result {
            case .Success:
                if let value = response.result.value {
                    let json = JSON(value)
                    self.firstCompletion(json, closer: completion)
                }
            case .Failure(let error):
                print(error)
            }
        }
    }
    func firstCompletion(data: JSON, closer: [[String:String]] -> () ) {
        var result = [ [String:String] ]()
        for item in data["results"].arrayValue {
            let firstName = item["first_name"].stringValue
            let lastName = item["last_name"].stringValue
            let id = item["bioguide_id"].stringValue
            var party = "Independent"
            switch item["party"].stringValue {
            case "D":
                party = "Democrat"
            case "R":
                party = "Republican"
            default:
                break // do nothing
            }
            let email = item["oc_email"].stringValue
            let web = item["website"].stringValue
            let twitterID = item["twitter_id"].stringValue
            let termEnd = item["term_end"].stringValue
            let dict = ["name": firstName + " " + lastName, "id": id,
                "party": party, "email": email, "website": web,
                "term_end": termEnd, "twitter": twitterID]
            result.append(dict)
        }
        print("Received JSON Response from Sunlight")
        closer(result)
    }
    
}

