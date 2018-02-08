package apns

import grails.plugins.*

class ApnsGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.3.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def author = "Sebastien Arbogast, Arthur Neves"
    def authorEmail = "sebastien.arbogast@gmail.com, arthurnn@gmail.com"
    def title = "Apple Push Notification Service"
    def description = '''\\
This plugin allows you to integrate with Apple Push Notification service to send
push notifications to an iPhone client of your Grails application.
'''

    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/apns"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "GPL3"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() { {->
        def configuredEnvironment
        if(application.config.apns.environment){
            configuredEnvironment = ApnsFactoryBean.Environment.valueOf(application.config.apns.environment.toUpperCase() as String)
        } else {
            switch(Environment.current){
                case Environment.PRODUCTION: configuredEnvironment = ApnsFactoryBean.Environment.PRODUCTION
                break
                default: configuredEnvironment = ApnsFactoryBean.Environment.SANDBOX
            }
        }

        apnsService(ApnsFactoryBean) {
            pathToCertificate = application.config.apns.pathToCertificate ?: null
            certificateResourcePath = application.config.apns.certificateResourcePath ?: null
            password = application.config.apns.password
            environment = configuredEnvironment
        }
    } }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
