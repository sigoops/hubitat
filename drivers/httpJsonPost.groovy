/*
 * Http Json Post
 *
 * Calls URIs with HTTP POST from a device
 * Based on `httpGetSwitch.groovy` from hubitat/HubitatPublic
 * 
 */

 import groovy.json.JsonSlurper
 import groovy.json.JsonOutput

metadata {
    definition(name: "Http Json Post", namespace: "sigoops", author: "SIGOOPS", importUrl: "https://raw.githubusercontent.com/sigoops/hubitat/main/drivers/httpJsonPost.groovy") {
        capability "Notification"
    }
}

preferences {
    section("URIs") {
        input "baseUrl", "text", title: "Base URL", required: true
        input "token", "text", title: "Bearer Token", required: true
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
    }
}

def logsOff() {
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable", [value: "false", type: "bool"])
}

def updated() {
    log.info "updated..."
    log.warn "debug logging is: ${logEnable == true}"
    if (logEnable) runIn(1800, logsOff)
}

def parse(String description) {
    if (logEnable) log.debug(description)
}

def deviceNotification(message) {
    if (logEnable) log.debug "Notification sent. Message: ${message}"
    try {
        def json = new JsonSlurper().parseText(message)
        def body = new groovy.json.JsonOutput().toJson(json.payload)
        def params = [
            uri: settings.baseUrl,
            path: json.endpoint,
            headers: [
                    "Content-Type": "application/json",
                    "Authorization": "Bearer ${settings.token}"
            ],
            body: json.payload
        ]
        httpPost(params) { resp ->
            if (resp.success) {
                
                sendEvent(name: "notification", value: json.payload)
            }
            if (logEnable)
                if (resp.data) log.debug "${resp.data}"
        }
    } catch (Exception e) {
        log.warn "Call send notification failed: ${e.message}"
    }
}