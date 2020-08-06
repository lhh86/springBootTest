package com.fh.controller;
import com.fh.common.jsonDate;
import com.fh.model.Shop;
import com.fh.model.ShopInfo;
import com.fh.service.ShopService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(description="商品controller")
@RestController
@RequestMapping("ShopController")
public class ShopController {
    @Autowired
    private ShopService shopService;

   /* @RequestMapping("queryHotShopData")
    // 如果查出的数据是从缓存中拿的
    public jsonDate queryHotShopData(){
        Jedis jedis = RedisUtil.getJedis();
        String hotProduct_jgxi = jedis.get("hotProduct_jgxi");
        if(StringUtils.isEmpty(hotProduct_jgxi)==true){
            List<ShopInfo> productInfos = shopService.queryHotShopData();
            hotProduct_jgxi = JSONObject.toJSONString(productInfos);
            jedis.set("hotProduct_jgxi",hotProduct_jgxi);
        }
        RedisUtil.returnJedis(jedis);
        return jsonDate.getJsonSuccess(hotProduct_jgxi);
    }*/
    //商品页
   @RequestMapping("queryShopAll")
   @ApiOperation("查询全部商品")
   @ApiImplicitParams({
          @ApiImplicitParam(name = "typeId", value = "类型ID", required = true, dataType = "long", paramType = "query")
   })
   @ApiResponses({
           @ApiResponse(code = 404,message = "请求路径不正确"),
           @ApiResponse(code = 666,message = "用户没有登录"),
   })
   public jsonDate queryShopAll(Integer typeId){
     List<Shop>  shops= shopService.queryShopAll(typeId);
     return jsonDate.getJsonSuccess(shops);
   }
    //商品详情页查询
   @RequestMapping("queryShopById")
   @ApiImplicitParams({@ApiImplicitParam(name="id",value = "ID",required = true ,dataType = "java.lang.String")})
    public jsonDate queryShopById(String id){
      ShopInfo slist = shopService.queryShopById(Integer.valueOf(id));
      return jsonDate.getJsonSuccess(slist);
   }
}
