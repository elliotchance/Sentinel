package org.sentinel.servers.http.protocol;

/**
 * The following is a list of Hypertext Transfer Protocol (HTTP) response status codes. This
 * includes codes from IETF internet standards as well as other IETF RFCs, other specifications and
 * some additional commonly used codes. The first digit of the status code specifies one of five
 * classes of response; the bare minimum for an HTTP client is that it recognises these five
 * classes. The phrases used are the standard examples, but any human-readable alternative can be
 * provided. Unless otherwise stated, the status code is part of the HTTP/1.1 standard.
 * 
 * <h1>1xx Informational</h1>
 * 
 * Request received, continuing process.
 * 
 * This class of status code indicates a provisional response, consisting only of the Status-Line
 * and optional headers, and is terminated by an empty line. Since HTTP/1.0 did not define any 1xx
 * status codes, servers must not send a 1xx response to an HTTP/1.0 client except under
 * experimental conditions.
 * 
 * <h1>2xx Success</h1>
 * 
 * This class of status codes indicates the action requested by the client was received, understood,
 * accepted and processed successfully.
 * 
 * <h1>3xx Redirection</h1>
 * 
 * The client must take additional action to complete the request.
 * 
 * This class of status code indicates that further action needs to be taken by the user agent in
 * order to fulfil the request. The action required may be carried out by the user agent without
 * interaction with the user if and only if the method used in the second request is GET or HEAD.
 * A user agent should not automatically redirect a request more than five times, since such
 * redirections usually indicate an infinite loop.
 * 
 * <h1>4xx Client Error</h1>
 * 
 * The 4xx class of status code is intended for cases in which the client seems to have erred.
 * Except when responding to a HEAD request, the server should include an entity containing an
 * explanation of the error situation, and whether it is a temporary or permanent condition.
 * These status codes are applicable to any request method. User agents should display any included
 * entity to the user.
 * 
 * <h1>5xx Server Error</h1>
 * 
 * The server failed to fulfill an apparently valid request.
 * 
 * Response status codes beginning with the digit "5" indicate cases in which the server is aware
 * that it has encountered an error or is otherwise incapable of performing the request. Except when
 * responding to a HEAD request, the server should include an entity containing an explanation of
 * the error situation, and indicate whether it is a temporary or permanent condition. Likewise,
 * user agents should display any included entity to the user. These response codes are applicable
 * to any request method.
 */
public enum HTTPStatusCode
{
    
    /**
     * This means that the server has received the request headers, and that the client should
     * proceed to send the request body (in the case of a request for which a body needs to be sent;
     * for example, a POST request). If the request body is large, sending it to a server when a
     * request has already been rejected based upon inappropriate headers is inefficient. To have a
     * server check if the request could be accepted based on the request's headers alone, a client
     * must send Expect: 100-continue as a header in its initial request[2] and check if a
     * 100 Continue status code is received in response before continuing (or receive
     * 417 Expectation Failed and not continue).
     */
    HTTP_100(100, "Continue"),
    
    /**
     * This means the requester has asked the server to switch protocols and the server is
     * acknowledging that it will do so.
     */
    HTTP_101(101, "Switching Protocols"),
    
    /**
     * As a WebDAV request may contain many sub-requests involving file operations, it may take a
     * long time to complete the request. This code indicates that the server has received and is
     * processing the request, but no response is available yet. This prevents the client from
     * timing out and assuming the request was lost.
     */
    HTTP_102(102, "Processing"),
    
    /**
     * Standard response for successful HTTP requests. The actual response will depend on the
     * request method used. In a GET request, the response will contain an entity corresponding to
     * the requested resource. In a POST request the response will contain an entity describing or
     * containing the result of the action.
     */
    HTTP_200(200, "OK"),
    
    /**
     * The request has been fulfilled and resulted in a new resource being created.
     */
    HTTP_201(201, "Created"),
    
    /**
     * The request has been accepted for processing, but the processing has not been completed. The
     * request might or might not eventually be acted upon, as it might be disallowed when
     * processing actually takes place.
     */
    HTTP_202(202, "Accepted"),
    
    /**
     * The server successfully processed the request, but is returning information that may be from
     * another source.
     */
    HTTP_203(203, "Non-Authoritative Information"),
    
    /**
     * The server successfully processed the request, but is not returning any content.
     */
    HTTP_204(204, "No Content"),
    
    /**
     * The server successfully processed the request, but is not returning any content. Unlike a 204
     * response, this response requires that the requester reset the document view.
     */
    HTTP_205(205, "Reset Content"),
    
    /**
     * The server is delivering only part of the resource due to a range header sent by the client.
     * The range header is used by tools like wget to enable resuming of interrupted downloads, or
     * split a download into multiple simultaneous streams.
     */
    HTTP_206(206, "Partial Content"),
    
    /**
     * The message body that follows is an XML message and can contain a number of separate response
     * codes, depending on how many sub-requests were made.
     */
    HTTP_207(207, "Multi-Status"),
    
    /**
     * The members of a DAV binding have already been enumerated in a previous reply to this
     * request, and are not being included again.
     */
    HTTP_208(208, "Already Reported"),
    
    /**
     * The server has fulfilled a GET request for the resource, and the response is a representation
     * of the result of one or more instance-manipulations applied to the current instance.
     */
    HTTP_226(226, "IM Used"),
    
    /**
     * Indicates multiple options for the resource that the client may follow. It, for instance,
     * could be used to present different format options for video, list files with different
     * extensions, or word sense disambiguation.
     */
    HTTP_300(300, "Multiple Choices"),
    
    /**
     * This and all future requests should be directed to the given URI.
     */
    HTTP_301(301, "Moved Permanently"),
    
    /**
     * This is an example of industry practice contradicting the standard. The HTTP/1.0
     * specification (RFC 1945) required the client to perform a temporary redirect (the original
     * describing phrase was "Moved Temporarily"), but popular browsers implemented 302 with the
     * functionality of a 303 See Other. Therefore, HTTP/1.1 added status codes 303 and 307 to
     * distinguish between the two behaviours. However, some Web applications and frameworks use the
     * 302 status code as if it were the 303.
     */
    HTTP_302(302, "Found"),
    
    /**
     * The response to the request can be found under another URI using a GET method. When received
     * in response to a POST (or PUT/DELETE), it should be assumed that the server has received the
     * data and the redirect should be issued with a separate GET message.
     */
    HTTP_303(303, "See Other"),
    
    /**
     * Indicates the resource has not been modified since last requested. Typically, the HTTP client
     * provides a header like the If-Modified-Since header to provide a time against which to
     * compare. Using this saves bandwidth and reprocessing on both the server and client, as only
     * the header data must be sent and received in comparison to the entirety of the page being
     * re-processed by the server, then sent again using more bandwidth of the server and client.
     */
    HTTP_304(304, "Not Modified"),
    
    /**
     * Many HTTP clients (such as Mozilla and Internet Explorer) do not correctly handle responses
     * with this status code, primarily for security reasons.
     */
    HTTP_305(305, "Use Proxy"),
    
    /**
     * No longer used. Originally meant "Subsequent requests should use the specified proxy."
     */
    HTTP_306(306, "Switch Proxy"),
    
    /**
     * In this case, the request should be repeated with another URI; however, future requests can
     * still use the original URI. In contrast to how 302 was historically implemented, the request
     * method should not be changed when reissuing the original request. For instance, a POST
     * request must be repeated using another POST request.
     */
    HTTP_307(307, "Temporary Redirect"),
    
    /**
     * The request, and all future requests should be repeated using another URI. 307 and 308 (as
     * proposed) parallel the behaviours of 302 and 301, but do not allow the HTTP method to change.
     * So, for example, submitting a form to a permanently redirected resource may continue
     * smoothly.
     */
    HTTP_308(308, "Permanent Redirect"),
    
    /**
     * The request cannot be fulfilled due to bad syntax.
     */
    HTTP_400(400, "Bad Request"),
    
    /**
     * Similar to 403 Forbidden, but specifically for use when authentication is required and has
     * failed or has not yet been provided.[2] The response must include a WWW-Authenticate header
     * field containing a challenge applicable to the requested resource. See Basic access
     * authentication and Digest access authentication.
     */
    HTTP_401(401, "Unauthorized"),
    
    /**
     * Reserved for future use. The original intention was that this code might be used as part of
     * some form of digital cash or micropayment scheme, but that has not happened, and this code
     * is not usually used. As an example of its use, however, Apple's MobileMe service generates a
     * 402 error ("httpStatusCode:402" in the Mac OS X Console log) if the MobileMe account is
     * delinquent.
     */
    HTTP_402(402, "Payment Required"),
    
    /**
     * The request was a valid request, but the server is refusing to respond to it. Unlike a
     * 401 Unauthorized response, authenticating will make no difference. On servers where
     * authentication is required, this commonly means that the provided credentials were
     * successfully authenticated but that the credentials still do not grant the client permission
     * to access the resource (e.g. a recognized user attempting to access restricted content).
     */
    HTTP_403(403, "Forbidden"),
    
    /**
     * The requested resource could not be found but may be available again in the future.
     * Subsequent requests by the client are permissible.
     */
    HTTP_404(404, "Not Found"),
    
    /**
     * A request was made of a resource using a request method not supported by that resource; for
     * example, using GET on a form which requires data to be presented via POST, or using PUT on a
     * read-only resource.
     */
    HTTP_405(405, "Method Not Allowed"),
    
    /**
     * The requested resource is only capable of generating content not acceptable according to the
     * Accept headers sent in the request.
     */
    HTTP_406(406, "Not Acceptable"),
    
    /**
     * The client must first authenticate itself with the proxy.
     */
    HTTP_407(407, "Proxy Authentication Required"),
    
    /**
     * The server timed out waiting for the request. According to W3 HTTP specifications: "The
     * client did not produce a request within the time that the server was prepared to wait. The
     * client MAY repeat the request without modifications at any later time."
     */
    HTTP_408(408, "Request Timeout"),
    
    /**
     * Indicates that the request could not be processed because of conflict in the request, such as
     * an edit conflict.
     */
    HTTP_409(409, "Conflict"),
    
    /**
     * Indicates that the resource requested is no longer available and will not be available again.
     * This should be used when a resource has been intentionally removed and the resource should be
     * purged. Upon receiving a 410 status code, the client should not request the resource again in
     * the future. Clients such as search engines should remove the resource from their indices.
     * Most use cases do not require clients and search engines to purge the resource, and a
     * "404 Not Found" may be used instead.
     */
    HTTP_410(410, "Gone"),
    
    /**
     * The request did not specify the length of its content, which is required by the requested
     * resource.
     */
    HTTP_411(411, "Length Required"),
    
    /**
     * The server does not meet one of the preconditions that the requester put on the request.
     */
    HTTP_412(412, "Precondition Failed"),
    
    /**
     * The request is larger than the server is willing or able to process.
     */
    HTTP_413(413, "Request Entity Too Large"),
    
    /**
     * The URI provided was too long for the server to process.
     */
    HTTP_414(414, "Request-URI Too Long"),
    
    /**
     * The request entity has a media type which the server or resource does not support. For
     * example, the client uploads an image as image/svg+xml, but the server requires that images
     * use a different format.
     */
    HTTP_415(415, "Unsupported Media Type"),
    
    /**
     * The client has asked for a portion of the file, but the server cannot supply that portion.
     * For example, if the client asked for a part of the file that lies beyond the end of the file.
     */
    HTTP_416(416, "Requested Range Not Satisfiable"),
    
    /**
     * The server cannot meet the requirements of the Expect request-header field.
     */
    HTTP_417(417, "Expectation Failed"),
    
    /**
     * This code was defined in 1998 as one of the traditional IETF April Fools' jokes, in RFC 2324,
     * Hyper Text Coffee Pot Control Protocol, and is not expected to be implemented by actual HTTP
     * servers.
     */
    HTTP_418(418, "I'm a teapot"),
    
    /**
     * Not part of the HTTP standard, but returned by the Twitter Search and Trends API when the
     * client is being rate limited. Likely related to 420 (cannabis culture). See also Other
     * services may wish to implement the 429 Too Many Requests response code instead.
     */
    HTTP_420(420, "Enhance Your Calm"),
    
    /**
     * The request was well-formed but was unable to be followed due to semantic errors.
     */
    HTTP_422(422, "Unprocessable Entity"),
    
    /**
     * The resource that is being accessed is locked.
     */
    HTTP_423(423, "Locked"),
    
    /**
     * The request failed due to failure of a previous request (e.g. a PROPPATCH).
     */
    HTTP_424_DEPENDENCY(424, "Failed Dependency"),
    
    /**
     * Indicates the method was not executed on a particular resource within its scope because some
     * part of the method's execution failed causing the entire method to be aborted.
     */
    HTTP_424_METHOD(424, "Method Failure"),
    
    /**
     * Defined in drafts of "WebDAV Advanced Collections Protocol", but not present in "Web
     * Distributed Authoring and Versioning (WebDAV) Ordered Collections Protocol".
     */
    HTTP_425(425, "Unordered Collection"),
    
    /**
     * The client should switch to a different protocol such as TLS/1.0.
     */
    HTTP_426(426, "Upgrade Required"),
    
    /**
     * The origin server requires the request to be conditional. Intended to prevent "the 'lost
     * update' problem, where a client GETs a resource's state, modifies it, and PUTs it back to the
     * server, when meanwhile a third party has modified the state on the server, leading to a
     * conflict."
     */
    HTTP_428(428, "Precondition Required"),
    
    /**
     * The user has sent too many requests in a given amount of time. Intended for use with rate
     * limiting schemes.
     */
    HTTP_429(429, "Too Many Requests"),
    
    /**
     * The server is unwilling to process the request because either an individual header field, or all the header fields collectively, are too large.
     */
    HTTP_431(431, "Request Header Fields Too Large"),
    
    /**
     * Used in Nginx logs to indicate that the server has returned no information to the client and
     * closed the connection (useful as a deterrent for malware).
     */
    HTTP_444(444, "No Response"),
    
    /**
     * A Microsoft extension. The request should be retried after performing the appropriate action.
     * Often search-engines or custom applications will ignore required parameters. Where no default
     * action is appropriate, the Aviongoo website sends a "HTTP/1.1 449 Retry with valid
     * parameters: param1, param2, . . ." response. The applications may choose to learn, or not.
     */
    HTTP_449(449, "Retry With"),
    
    /**
     * A Microsoft extension. This error is given when Windows Parental Controls are turned on and
     * are blocking access to the given webpage.
     */
    HTTP_450(450, "Blocked by Windows Parental Controls"),
    
    /**
     * Defined in the internet draft "A New HTTP Status Code for Legally-restricted Resources".
     * Intended to be used when resource access is denied for legal reasons, e.g. censorship or
     * government-mandated blocked access. A reference to the 1953 dystopian novel Fahrenheit 451,
     * where books are outlawed.
     */
    HTTP_451_LEGAL(451, "Unavailable For Legal Reasons"),
    
    /**
     * Used in Exchange ActiveSync if there either is a more efficient server to use or the server
     * can't access the users' mailbox. The client is supposed to re-run the HTTP Autodiscovery
     * protocol to find a better suited server.
     */
    HTTP_451_REDIRECT(451, "Redirect"),
    
    /**
     * Nginx internal code similar to 431 but it was introduced earlier.
     */
    HTTP_494(494, "Request Header Too Large"),
    
    /**
     * Nginx internal code used when SSL client certificate error occurred to distinguish it from
     * 4XX in a log and an error page redirection.
     */
    HTTP_495(495, "Cert Error"),
    
    /**
     * Nginx internal code used when client didn't provide certificate to distinguish it from 4XX in
     * a log and an error page redirection.
     */
    HTTP_496(496, "No Cert"),
    
    /**
     * Nginx internal code used for the plain HTTP requests that are sent to HTTPS port to
     * distinguish it from 4XX in a log and an error page redirection.
     */
    HTTP_497(497, "HTTP to HTTPS"),
    
    /**
     * Used in Nginx logs to indicate when the connection has been closed by client while the server
     * is still processing its request, making server unable to send a status code back.
     */
    HTTP_499(499, "Client Closed Request"),
    
    /**
     * A generic error message, given when no more specific message is suitable.
     */
    HTTP_500(500, "Internal Server Error"),
    
    /**
     * The server either does not recognize the request method, or it lacks the ability to fulfill
     * the request.
     */
    HTTP_501(501, "Not Implemented"),
    
    /**
     * The server was acting as a gateway or proxy and received an invalid response from the
     * upstream server.
     */
    HTTP_502(502, "Bad Gateway"),
    
    /**
     * The server is currently unavailable (because it is overloaded or down for maintenance).
     * Generally, this is a temporary state.
     */
    HTTP_503(503, "Service Unavailable"),
    
    /**
     * The server was acting as a gateway or proxy and did not receive a timely response from the
     * upstream server.
     */
    HTTP_504(504, "Gateway Timeout"),
    
    /**
     * The server does not support the HTTP protocol version used in the request.
     */
    HTTP_505(505, "HTTP Version Not Supported"),
    
    /**
     * Transparent content negotiation for the request results in a circular reference.
     */
    HTTP_506(506, "Variant Also Negotiates"),
    
    /**
     * The server is unable to store the representation needed to complete the request.
     */
    HTTP_507(507, "Insufficient Storage"),
    
    /**
     * The server detected an infinite loop while processing the request (sent in lieu of 208).
     */
    HTTP_508(508, "Loop Detected"),
    
    /**
     * This status code, while used by many servers, is not specified in any RFCs.
     */
    HTTP_509(509, "Bandwidth Limit Exceeded"),
    
    /**
     * Further extensions to the request are required for the server to fulfill it.
     */
    HTTP_510(510, "Not Extended"),
    
    /**
     * The client needs to authenticate to gain network access. Intended for use by intercepting
     * proxies used to control access to the network (e.g. "captive portals" used to require
     * agreement to Terms of Service before granting full Internet access via a Wi-Fi hotspot).
     */
    HTTP_511(511, "Network Authentication Required"),
    
    /**
     * This status code is not specified in any RFCs, but is used by Microsoft Corp. HTTP proxies to
     * signal a network read timeout behind the proxy to a client in front of the proxy.
     */
    HTTP_598(598, "Network read timeout error"),
    
    /**
     * This status code is not specified in any RFCs, but is used by Microsoft Corp. HTTP proxies to
     * signal a network connect timeout behind the proxy to a client in front of the proxy.
     */
    HTTP_599(599, "Network connect timeout error");
    
    protected int statusCode;
    
    protected String statusMessage;

    HTTPStatusCode(int statusCode, String statusMessage)
    {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getStatusMessage()
    {
        return statusMessage;
    }

    @Override
    public String toString()
    {
        return statusCode + " " + statusMessage;
    }
    
    public static HTTPStatusCode valueOf(int statusCode) throws NoSuchHTTPStatusCodeException
    {
        HTTPStatusCode[] codes = HTTPStatusCode.values();
        for(HTTPStatusCode sc : codes) {
            if(sc.getStatusCode() == statusCode) {
                return sc;
            }
        }
        throw new NoSuchHTTPStatusCodeException(String.valueOf(statusCode));
    }
    
}
