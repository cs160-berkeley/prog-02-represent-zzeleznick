//
//  DetailViewController.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController, UIScrollViewDelegate {
    
    @IBOutlet weak var myImage: UIImageView!
    
    @IBOutlet weak var name: UILabel!
    
    @IBOutlet weak var party: UILabel!
    
    @IBOutlet weak var endDate: UILabel!
    
    @IBOutlet var scroller: UIScrollView!
    
    @IBOutlet weak var committees: UITextView!
    
    @IBOutlet weak var recentBills: UITextView!
    
    var text : String = "Text"
    var imageLoaded:Bool = false
    var twitterHandle: String!
    var newImage: UIImage!
    var repDict: [String:String]!
    
    var committeeResult: [[String:String]]!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        // name.text = text
        if imageLoaded{
            myImage?.image = newImage
        }
        else {
            myImage?.image = UIImage(named: "kitten")!
        }
        if repDict != nil {
            print(repDict)
            name.text = repDict["name"]
            party.text = repDict["party"]
            endDate.text = repDict["term_end"]
            twitterHandle = repDict["twitter"]
        }
        else {
            print("ERROR: RepDict not Loaded")
        }
    }
    
    override func viewDidLayoutSubviews() {
        // pass
        self.scroller.contentSize = CGSizeMake(view.frame.width, 1.2*view.frame.height)
        // view.frame.size;
        //CGSizeMake(2000, 2000);
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let dest : TimelineViewController = segue.destinationViewController as! TimelineViewController
        print("Moving to timeline for \(self.twitterHandle)")
        dest.name = self.twitterHandle
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}