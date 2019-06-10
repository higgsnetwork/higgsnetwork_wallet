package com.higgs.network.wallet.common.stub;

import java.util.Map;
import java.util.HashMap;

/**
 * @author :
 * @date :
 */
public class ResultMessage {

    /**
     * RESULTMSGBOX
     */
    public static Map<Integer, String> RESULTMSGBOX = new HashMap<Integer, String>();

    private static final String MSG_SUCCESS = "Completed successfully";
    private static final String MSG_BAD_REQUEST= "Bad Request!";
    private static final String MSG_UNAUTHORIZED= "Unauthorized!";
    private static final String MSG_FORBIDDEN= "Forbidden!";
    private static final String MSG_NOT_FOUND= "Not found!";
    private static final String MSG_TIMEOUT= "Timeout!";
    private static final String MSG_SERVER_ERROR_= "Server error!";
    private static final String MSG_SERVICE_UNAVAILABLE= "Service unavailable!";
    private static final String MSG_FAILED= "Failed!";

    private static final String MSG_IP_Error= "IP Error";
    private static final String MSG_Parameter_Null= "Parameter Null";
    private static final String MSG_Parameter_Error= "Parameter Error";
    private static final String MSG_Sign_Error= "Sign Error";
    private static final String MSG_Protocol_Error= "Protocol Error";
    private static final String MSG_Exception_Error= "Exception Error";
    private static final String MSG_Game_Not_Open= "Game Not Open";
    private static final String MSG_Balance_Error= "Balance Error";
    private static final String MSG_Request_Error= "Request Error";
    private static final String MSG_Request_TimeOut= "Request TimeOut";
    private static final String MSG_Error= "Error";
    private static final String MSG_Partner_Not_Openr= "Partner Not Open";

    private static final String MSG_ExUser_NotExist= "UserNotExist";
    private static final String MSG_NotBind_Error= "NotBind Error";
    private static final String MSG_VerifyCode_TimeOut= "VerifyCode TimeOut";
    private static final String MSG_Account_Freezing= "Account Freezing";
    private static final String MSG_VerifyCode_Fail= "VerifyCode Fail";
    private static final String MSG_User_NotExist= "UserNotExist";
    private static final String MSG_Token_SymbolError = "Token Symbol Error";
    private static final String MSG_Amount_Error = "Amount Error";
    private static final String MSG_Order_Processing = "Order Processing";
    private static final String MSG_Order_Fail = "Order Fail";
    private static final String MSG_Bind_BitAccount_Error = "Bind BitAccount Error";
    private static final String MSG_Unbind_Account = "Please untie it first";
    private static final String MSG_GoogleCode_Fall = "Google code fail";
    private static final String MSG_GoogleCode_Not_Bind = "Google code not bind";
    private static final String MSG_Fees_Error = "Transaction fees Error";
    private static final String MSG_Order_Exist = "OrderNo exist";
    private static final String MSG_Order_Finish = "Order Finish";

    static {
        RESULTMSGBOX.put(ResultCode.SUCCESS, MSG_SUCCESS);
        RESULTMSGBOX.put(ResultCode.BAD_REQUEST ,MSG_BAD_REQUEST);
        RESULTMSGBOX.put(ResultCode.UNAUTHORIZED, MSG_UNAUTHORIZED);
        RESULTMSGBOX.put(ResultCode.FORBIDDEN, MSG_FORBIDDEN);
        RESULTMSGBOX.put(ResultCode.NOT_FOUND, MSG_NOT_FOUND);
        RESULTMSGBOX.put(ResultCode.TIMEOUT, MSG_TIMEOUT);
        RESULTMSGBOX.put(ResultCode.SERVER_ERROR, MSG_SERVER_ERROR_);
        RESULTMSGBOX.put(ResultCode.SERVICE_UNAVAILABLE ,MSG_SERVICE_UNAVAILABLE);
        RESULTMSGBOX.put(ResultCode.FAILED ,MSG_FAILED);

        RESULTMSGBOX.put(ResultCode.IPError ,MSG_IP_Error);
        RESULTMSGBOX.put(ResultCode.ParameterNull, MSG_Parameter_Null);
        RESULTMSGBOX.put(ResultCode.ParameterError, MSG_Parameter_Error);
        RESULTMSGBOX.put(ResultCode.SignError, MSG_Sign_Error);
        RESULTMSGBOX.put(ResultCode.ProtocolError, MSG_Protocol_Error);
        RESULTMSGBOX.put(ResultCode.ExceptionError, MSG_Exception_Error);
        RESULTMSGBOX.put(ResultCode.RequestError ,MSG_Request_Error);
        RESULTMSGBOX.put(ResultCode.RequestTimeOut ,MSG_Request_TimeOut);
        RESULTMSGBOX.put(ResultCode.Error ,MSG_Error);
        RESULTMSGBOX.put(ResultCode.PartnerNotOpen ,MSG_Partner_Not_Openr);
        RESULTMSGBOX.put(ResultCode.SymbolError ,MSG_Token_SymbolError);
        RESULTMSGBOX.put(ResultCode.AmountError ,MSG_Amount_Error);
        RESULTMSGBOX.put(ResultCode.OrderProcessing, MSG_Order_Processing);
        RESULTMSGBOX.put(ResultCode.OrderFail, MSG_Order_Fail);
        RESULTMSGBOX.put(ResultCode.FeesError ,MSG_Fees_Error);
        RESULTMSGBOX.put(ResultCode.OrderExist,MSG_Order_Exist);
        RESULTMSGBOX.put(ResultCode.OrderFinish,MSG_Order_Finish);

//        RESULTMSGBOX.put(ResultCode.GameNotOpen ,MSG_Game_Not_Open);
//        RESULTMSGBOX.put(ResultCode.BalanceError ,MSG_Balance_Error);
//        RESULTMSGBOX.put(ResultCode.ExUserNotExist ,MSG_ExUser_NotExist);
//        RESULTMSGBOX.put(ResultCode.NotBindError ,MSG_NotBind_Error);
//        RESULTMSGBOX.put(ResultCode.VerifyCodeTimeOut ,MSG_VerifyCode_TimeOut);
//        RESULTMSGBOX.put(ResultCode.AccountFreezing ,MSG_Account_Freezing);
//        RESULTMSGBOX.put(ResultCode.VerifyCodeFail ,MSG_VerifyCode_Fail);
//        RESULTMSGBOX.put(ResultCode.UserNotExist ,MSG_User_NotExist);
//        RESULTMSGBOX.put(ResultCode.BindBitAccountError ,MSG_Bind_BitAccount_Error);
//        RESULTMSGBOX.put(ResultCode.UnbindAccount ,MSG_Unbind_Account);
//        RESULTMSGBOX.put(ResultCode.GoogleCodeFall ,MSG_GoogleCode_Fall);
//        RESULTMSGBOX.put(ResultCode.GoogleCodeNotBind ,MSG_GoogleCode_Not_Bind);
    }

}
