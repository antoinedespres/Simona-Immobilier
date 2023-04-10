package com.simona.housing.client;

import com.simona.housing.JsonUtil;
import com.simona.housing.dto.ApiResponse;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

// Convert the response body to ApiResponse
public class FeignResultDecoder  implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response.body() == null) {
            throw new DecodeException(response.status(), "No valid data returned" , response.request());
        }

        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        ApiResponse result =  JsonUtil.json2obj(bodyStr, type);
        return result;
    }
}
