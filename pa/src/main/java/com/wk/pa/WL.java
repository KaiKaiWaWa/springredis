package com.wk.pa;/**
 * Created by yhopu-pc2 on 2018/7/31.
 */

import com.pajk.justice.openapi.biz.common.JkClient;
import com.pajk.justice.openapi.biz.common.QueryEnv;
import com.pajk.justice.openapi.biz.model.reuslt.trade.b2c.BatchQueryB2CCarrierResult;
import com.pajk.justice.openapi.biz.request.trade.b2c.BatchQueryB2CCarrierRequest;

/**
 * @author wk
 * @className WL
 **/
public class WL {
    public static void main(String[] args) {
        JkClient jkClient = new JkClient("yanghuopu", "13b677b1f799511f1300cff5f71031c3", QueryEnv.PROD);
        BatchQueryB2CCarrierRequest request = new BatchQueryB2CCarrierRequest();
        BatchQueryB2CCarrierResult result = jkClient.execute(request);
        System.out.println(result);
    }
}
