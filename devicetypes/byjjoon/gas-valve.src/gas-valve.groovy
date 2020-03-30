/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
import groovy.json.*
import groovy.json.JsonSlurper

metadata {
    definition (name: "Gas Valve", namespace: "ByJJoon", author: "ByJJoon", mnmn: "SmartThings", vid: "generic-valve") {
        capability "Valve"
    }
    
    preferences {
        input "ip", "text", type: "text", title: "IP:PORT", description: "enter address must be [ip]:[port] ", required: true
    }
}

def installed(){
    log.debug "installed()"
    init()
}

def uninstalled(){
    log.debug "uninstalled()"
    unschedule()
}

def updated(){
    log.debug "updated()"
    unschedule()
    init()
}

def init(){
    refresh()
    runEvery1Minute(refresh)
}

def refresh(){
    //log.debug "refresh()"

    if(ip){
        def options = [
                "method": "GET",
                "path": "/Gas",
                "headers": ["HOST": "${ip}"]
        ]

        def myhubAction = new physicalgraph.device.HubAction(options, null, [callback: update_data])
        sendHubCommand(myhubAction)
    }
    else log.error "설정에 IP 주소를 입력하세요!"
}

def update_data(physicalgraph.device.HubResponse hubResponse){
    def msg
    try {
        msg = parseLanMessage(hubResponse.description)
        def resp = new JsonSlurper().parseText(msg.body)

        if(resp.gasvalve == 'open') {
            sendEvent(name: "valve", value: "open")
            //log.debug "가스밸브 상태 : open"
        }
        else{
            sendEvent(name: "valve", value: "closed")
            //log.debug "가스밸브 상태 : close"
        }

    } catch (e) {
        log.error "Exception caught while parsing data: "+e;
    }
}

def close_valve(){
    def options = [
            "method": "GET",
            "path": "/Close_Valve",
            "headers": ["HOST": "${ip}"]
    ]

    def myhubAction = new physicalgraph.device.HubAction(options, null, [callback: update_data])
    sendHubCommand(myhubAction)
}

def open(){
    refresh()
    log.debug "open()"
}

def close(){
    close_valve()
    log.debug "가스밸브 잠금"
}