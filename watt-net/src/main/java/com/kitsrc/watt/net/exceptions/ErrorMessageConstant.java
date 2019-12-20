

package com.kitsrc.watt.net.exceptions;

public class ErrorMessageConstant {

    public static final String SDK_ENDPOINT_MANAGEMENT_DOC_HTML =
            "https://www.alibabacloud.com/help/zh/doc-detail/92049.htm";
            
    public static final String INVALID_REGION_ID =
            "No such region '%s'. Please check your region ID.";
    public static final String ENDPOINT_NO_REGION =
            "No endpoint in the region '%s' for product '%s'. \n" +
                    "You can set an endpoint for your request explicitly.%s\n" +
                    "See " + SDK_ENDPOINT_MANAGEMENT_DOC_HTML + "\n";

    /**
     * Or use available regions:
     */
    public static final String ENDPOINT_NO_PRODUCT =
            "No endpoint for product '%s'. \n" +
                    "Please check the product code, or set an endpoint for your request explicitly.\n" +
                    "See " + SDK_ENDPOINT_MANAGEMENT_DOC_HTML + "\n";

    public static final String SERVER_RESPONSE_HTTP_BODY_EMPTY =
            "Failed to parse the response. The request was succeeded, but the server returned an empty HTTP body.";
}
