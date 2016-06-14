package lk.dialog.ideabiz.library.APIHandler;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.ideabiz.api.model.common.sms.Inbound.InboundSMSRequestWrap;
import lk.ideabiz.api.model.common.sms.Outbound.OutboundSMSMessageRequest;
import lk.ideabiz.api.model.common.sms.Outbound.OutboundSMSMessagingRequestWrap;
import lk.dialog.ideabiz.library.APICall.APICall;
import lk.dialog.ideabiz.library.APIHandler.model.SMS.SMSMessage;
import lk.dialog.ideabiz.library.model.APICall.APICallResponse;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Malinda_07654 on 6/10/2016.
 */
public class SMSHandler {

    public String URL;
    public APICall apiCalll;
    public Integer OAuthId;
    public String senderName;
    public String senderAddress;
    public Gson gson;
    public Logger logger;

    //sending sms with default sender name and address
    public boolean sendSMS(String msisdn, String message) throws Exception {
        return sendSMS(senderAddress, senderName, msisdn, message);
    }

    //sending sms with custom sender name and address
    public boolean sendSMS(String port, String senderName, String msisdn, String message) throws Exception {

        //Creating outbound request
        OutboundSMSMessagingRequestWrap outboundSMSMessagingRequestWrap = new OutboundSMSMessagingRequestWrap();
        OutboundSMSMessageRequest outboundSMSMessageRequest = new OutboundSMSMessageRequest(
                msisdn,
                message, port, senderName);
        outboundSMSMessagingRequestWrap.setOutboundSMSMessageRequest(outboundSMSMessageRequest);

        //Setting headers
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");

        //Sending API call
        APICallResponse response = null;
        try {
            response = apiCalll.sendAuthAPICall(OAuthId, URL, "POST", header, gson.toJson(outboundSMSMessagingRequestWrap), false);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
        return true;
    }

    /***
     * Read inbound sms
     *
     * @param sms
     * @return
     */
    public SMSMessage readSMS(String sms) {
        InboundSMSRequestWrap inboundSMSRequestWrap = gson.fromJson(sms, InboundSMSRequestWrap.class);
        if (inboundSMSRequestWrap == null)
            return null;

        SMSMessage smsMessage = new SMSMessage();
        smsMessage.setDestinationAddress(inboundSMSRequestWrap.getInboundSMSMessageNotification().getInboundSMSMessage().getDestinationAddress());
        smsMessage.setSourceAddress(inboundSMSRequestWrap.getInboundSMSMessageNotification().getInboundSMSMessage().getSenderAddress());
        smsMessage.setMessage(inboundSMSRequestWrap.getInboundSMSMessageNotification().getInboundSMSMessage().getMessage());
        smsMessage.setMessageId(inboundSMSRequestWrap.getInboundSMSMessageNotification().getInboundSMSMessage().getMessageId());

        return smsMessage;

    }

    /***
     * @param URL           SMS API URL
     * @param apiCalll      OAuth API hanndler
     * @param OAuthId       OAuth ID
     * @param senderName    Default sender name
     * @param senderAddress Default sender address
     */
    public SMSHandler(String URL, APICall apiCalll, Integer OAuthId, String senderName, String senderAddress) {
        this.URL = URL;
        this.apiCalll = apiCalll;
        this.OAuthId = OAuthId;
        this.senderName = senderName;
        this.senderAddress = senderAddress;

        gson = new GsonBuilder().serializeNulls().create();
    }
}
