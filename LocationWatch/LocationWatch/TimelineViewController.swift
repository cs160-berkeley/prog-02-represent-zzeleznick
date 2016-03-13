//
//  TimelineViewController.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import UIKit
import TwitterKit

class TimelineViewController: TWTRTimelineViewController {
    
    var name: String = "fabric"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let client = TWTRAPIClient()
        self.dataSource = TWTRUserTimelineDataSource(screenName: name, APIClient: client)
    }
}