# Introduction #

This page is a rough sketch of the order of events in the Seam life cycle on an initial request.

## Before _Restore View_ ##
  * beforeServletPhase
    * beforeRestoreView
      * FacesLifecycle.beginRequest
    * handleTransactionsBeforePhase
      * begin only if JTA
  * raiseEventsBeforePhase
## After _Restore View_ ##
  * raiseEventsAfterPhase
    * afterServletPhase
      * afterRestoreView
        * restore/activate long running conversation or intialize temporary one (if long running conversation cannot be restored, redirect to no conversation view)
        * begin/join/end conversation
        * validate jBPM page flow
        * Pages.postRestore
          * store page parameters in view root, enforcing required and performing conversions and validations
          * enforce any conversation or login required for page
          * enforce restore restriction for each matched page; redirect to login or throw not authorized
          * apply page parameters to model
      * handleTransactionsAfterPhase
        * commit only if response has been marked as complete
## Before _Restore Response_ ##
  * beforeServletPhase
    * handleTransactionsBeforePhase
      * begin transaction
    * beforeRender
      * flush page context
      * preRenderPage
        * temporarily enter INVOKE\_APPLICATION phase
        * Pages.preRender
          * redirect if changing scheme (http -> https)
          * select data model row
          * enforce conversation required and logins
          * enforce render restriction for each matched page; redirect to login or throw not authorized
          * begin/end conversations, tasks or processes
          * do injections
          * call any actions
          * perform navigation
          * call GET action (in s:link or s:button)
        * go back to RENDER\_REPONSE phase
        * handleTransactionsAfterPageActions
          * commit
          * begin (if not response complete)
      * migrate Seam-managed JSF messages to FacesContext
      * prepare conversation backswitch
      * store conversation/page flow
      * change flush mode of persistence contexts
## After _Restore Response_ ##
  * afterServletPhase
    * handleTransactionsAfterPhase
      * commit
    * afterRender
      * prepare conversation switch
      * restore flush mode
      * FacesLifecycle.endRequest