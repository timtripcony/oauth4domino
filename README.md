oauth4domino
============

OSGi plugin and NSF bundle to facilitate allowing IBM Domino applications to act as valid OAuth providers

## Quick Start
*Additional detail on each of the following steps is available (or will be soon) in the project wiki*

### Install XSP Library
* Open org.openntf.xsp.oauth.update/site.xml
* On the Site Map tab, click Build All
* Add org.openntf.xsp.oauth.update as a folder update site in Domino Designer
* Install the library plugin within Designer
* Import the update site into an NSF Update Site database
* Restart HTTP

### Create Resource Registry and Token Store
* Import org.openntf.xsp.oauth.registry into Designer as an existing project
* Create a new NSF instance from the imported on-disk project
* Replicate the new instance to any server with the XSP Library installed &mdash;
or create another instance based on the template
* *Optional*: copy the `codes` and `tokens` views from the registry NSF to a separate NSF.
By default, the registry can serve as a valid token store. If you prefer to keep these functions separate,
these views can safely be removed from the registry after they have been duplicated in the separate token NSF.


### Populate Resource Registry
Using the XPage interface for the registry NSF:
* define at least one resource container
* *Optional*: define at least one custom permission for each container &mdash;
if no custom permissions are defined, each end user will be asked to grant the default
"Basic Profile Information" read permission
* define at least one client for each container

### Configure Authorization Container
This is the NSF that the user will be redirected to when a client application requests their permission for access to a resource container.

Copy the `permissionPrompt` Custom Control from the registry template and add it to any XPage of your choosing. This can be as global as a
single XPage for your entire domain &mdash; or as varied as a separate XPage for each resource container &mdash; and should contain any
branding deemed appropriate to ensuring users that they are in the right place and presented with a clean, modern interface.

Only one characteristic of this portion of the configuration is non-negotiable: anonymous users may not access this page directly.
By the time the user sees the `Authorize` and `Cancel` buttons, they **must** have already been authenticated by Domino.

### Configure Resource Container
Make the following changes to the xsp.properties file of any NSF that will allow OAuth access to its API:
* Specify a dependency on the XSP Library, either in the XPage Libraries section of the Page Generation tab of the GUI editor,
or by manually adding the library name on the Source tab; e.g.:
      xsp.library.depends=org.openntf.xsp.oauth.library
* Add the following lines to the Source:
      xsp.oauth.registry=oauth/registry.nsf
      xsp.oauth.tokenstore=oauth/token.nsf
  **NOTE**: these settings can both refer to the same NSF if it contains all the necessary design elements for both functions,
  and can be any valid NSF path.

  In fact, these settings intentionally support the `serverName!!path/to/app.nsf` syntax.
  This facilitates the option to allow the registry, token store, authorization container, and resource container to be stored on almost
  any combination of servers.

  There is only one practical limitation to this flexibility: the authorization container and resource container need not reside on the
  same Domino server, but both must be accessible to the resource owner and client application. This typically implies that the server
  resides "outside the firewall" &mdash; either in a DMZ or otherwise publicly accessible via HTTP without supplying network credentials.

  Conversely, the registry and token store &mdash; indeed, the actual data that is created and accessed by the resource container &mdash;
  may be *inside* a firewall... as long as they are still accessible to the authorization and resource containers. With this hybrid
  configuration, no data can be accessed directly, but a secure broker is now in place allowing resource owners to manage their data from
  anywhere via any software or third party service that they have expressly granted permission to assist them in managing that data.


### Reference the API
#### In Java:
    OAuthSession session = OAuthProvider.getSession();
    if (!session.isAnonymous()) {
      // session is now the resource owner
      if (session.isGrantedPermission("send_email")) {
        /*
         * the end user granted permission to the client app
         * to send email on their behalf, so it's safe to use
         * the session variable to do so
         */
      } else {
        /* the client app has not been granted permission to
         * send email on the user's behalf, so if we do still
         * need to send email, we should use sessionAsSigner
         * to ensure we're respecting the user's wishes
         */
      }
    } else {
      // do nothing unless anonymous access is allowed
    }
#### In SSJS:
    if (!sessionFromAccessToken.isAnonymous()) {
      // session is now the resource owner
      if (sessionFromAccessToken.isGrantedPermission("send_email")) {
        /*
         * the end user granted permission to the client app
         * to send email on their behalf, so it's safe to use
         * the sessionFromAccessToken variable to do so
         */
      } else {
        /* the client app has not been granted permission to
         * send email on the user's behalf, so if we do still
         * need to send email, we should use sessionAsSigner
         * to ensure we're respecting the user's wishes
         */
      }
    } else {
      // do nothing unless anonymous access is allowed
    }
