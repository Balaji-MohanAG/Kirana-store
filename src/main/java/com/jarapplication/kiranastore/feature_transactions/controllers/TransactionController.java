package com.jarapplication.kiranastore.feature_transactions.controllers;

import com.jarapplication.kiranastore.constants.HttpStatusCode;
import com.jarapplication.kiranastore.feature_transactions.model.PurchaseResponse;
import com.jarapplication.kiranastore.feature_transactions.model.RefundRequest;
import com.jarapplication.kiranastore.feature_transactions.service.TransactionServiceImpl;
import com.jarapplication.kiranastore.feature_transactions.model.PurchaseRequest;
import com.jarapplication.kiranastore.response.ApiResponse;
import com.jarapplication.kiranastore.utils.JwtUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/v1/api")
public class TransactionController {

    private final TransactionServiceImpl transactionServiceImpl;
    private final JwtUtil jwtUtil;
    @Autowired
    public TransactionController(TransactionServiceImpl transactionServiceImpl, JwtUtil jwtUtil) {
    this.transactionServiceImpl = transactionServiceImpl;
    this.jwtUtil = jwtUtil;
    }

    /**
     * Makes a Refund when provided with bill Id
     * @param token
     * @param request
     * @return
     * @throws JSONException
     */
    @PostMapping("/refund")
    public ApiResponse refund(@RequestHeader("Authorization") String token , @RequestBody RefundRequest request) throws JSONException {
        String jwt = token.replace("Bearer ", "");
        String userId = jwtUtil.extractUserId(jwt);
        String billId = request.getBillId();
        transactionServiceImpl.makeRefund(billId, userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData("Refunded");
        apiResponse.setStatus(HttpStatusCode.OK);
        return apiResponse;
    }

    /**
     * Generates a bill of required products and saves the transactions
     * @param token
     * @param request
     * @return
     * @throws JSONException
     */
    @PostMapping("/purchase")
    public ApiResponse purchase(@RequestHeader("Authorization") String token, @RequestBody PurchaseRequest request) throws JSONException {

        String jwt = token.replace("Bearer ", "");
        String UserId = jwtUtil.extractUserId(jwt);
        String userName = jwtUtil.extractUsername(jwt);
        request.setUserId(UserId);
        PurchaseResponse response = transactionServiceImpl.makePurchase(request);
        response.setUserName(userName);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatusCode.OK);
        return apiResponse;
    }
}


