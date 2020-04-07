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
    definition (name: "Light", namespace: "ByJJoon", author: "ByJJoon", vid: "generic-switch") {
        capability "Switch"
    }
    
    preferences {
        input "ip", "text", type: "text", title: "IP:PORT", description: "enter address must be [ip]:[port]", required: true
        input "light_num", "text", type: "text", title: "Light Number", description: "light1 ~ light7", required: true
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
                "path": "/Light",
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
        /*
        # light1 : 거실 1
        # light2 : 거실 2
        # light3 : 거실 3
        # light4 : 방1
        # light5 : 방2
        # light6 : 방3
        # light7 : 방4
        */
        if(resp[room] == 'on') {
            sendEvent(name: "switch", value: "on")
            //log.debug "전등 상태 : 켜짐"
        }
        else{
            sendEvent(name: "switch", value: "off")
            //log.debug "전등 상태 : 꺼짐"
        }

    } catch (e) {
        log.error "Exception caught while parsing data: "+e;
    }
}

def on(){
    def options = [
            "method": "GET",
            "path": "/Control_Light?light=" + light_num + "&action=on",
            "headers": ["HOST": "${ip}"]
    ]

    def myhubAction = new physicalgraph.device.HubAction(options, null)
    sendHubCommand(myhubAction)
    sendEvent(name: "switch", value: "on")
    log.debug "전등 스위치 : ON"
}

def off(){
    def options = [
            "method": "GET",
            "path": "/Control_Light?light=" + light_num + "&action=off",
            "headers": ["HOST": "${ip}"]
    ]

    def myhubAction = new physicalgraph.device.HubAction(options, null)
    sendHubCommand(myhubAction)
    sendEvent(name: "switch", value: "off")
    log.debug "전등 스위치 : OFF"
}