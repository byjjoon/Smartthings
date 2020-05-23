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
    definition (name: "Heating", namespace: "ByJJoon", author: "ByJJoon", vid: "generic-radiator-thermostat-2") {
        capability "Thermostat"
        capability "Thermostat Heating Setpoint"
        capability "Thermostat Cooling Setpoint"
        capability "Temperature Measurement"
    }
    
    preferences {
        input "ip", "text", type: "text", title: "IP:PORT", description: "enter address must be [ip]:[port]", required: true
        input "heating", "text", type: "text", title: "Heating Number", description: "heating1 ~ heating5", required: true
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
                "path": "/Heating?heating=" + heating,
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
        # heating1 : 거실
        # heating2 : 방1
        # heating3 : 방2
        # heating4 : 방3
        # heating5 : 방4
        */
        sendEvent(name: "temperature", value: resp.current, unit: temperatureScale, displayed: true)
        //log.debug "현재 온도 : ${resp.current}"
        //sendEvent(name: "heatingSetpoint", value: resp.set, unit: temperatureScale, displayed: true)
        //log.debug "설정 온도 : ${resp.set}"

    } catch (e) {
        log.error "Exception caught while parsing data: "+e;
    }
}


def setHeatingSetpoint(set_temp) {
    def current = device.latestValue("temperature")
    //def current = device.latestValue("temperature") as Integer
    //def temp = set_temp as Integer

    def options = [
            "method": "GET",
            "path": "/Control_Heating?heating=" + heating + "&current=" + current + "&set=" + set_temp,
            "headers": ["HOST": "${ip}"]
    ]

    def myhubAction = new physicalgraph.device.HubAction(options, null)
    sendHubCommand(myhubAction)
      
    sendEvent(name: "heatingSetpoint", value: temp, unit: temperatureScale, displayed: true)
    log.debug "setHeatingSetpoint()"
}