**1. 申请新的地址**
---
**版本信息**
---

版本 | 时间 | 备注
---|---|---
1.0 | 2019-03-15
1.1 | 2019-04-18|增加partnerUid参数，调整返回值
1.2 | 2019-05-21|增加对IOST主链的支持，返回值address在IOST

**描述**
合作方的每一个用户申请地址时，通一个主链只需要申请一个地址，比如用户已经拥有了ETH地址，ETH链上其他代币通用这个ETH地址。
建议用户每次查看自己钱包地址时都调用一次此接口，如数据发生变化需要更新用户地址绑定关系。
### 特别说明
```
IOST主链的地址由address和number两部分构成其中address在higgs系统中是两个固定值。
在本接口返回值中的address参数内容为固定地址+|+分配给用户的number值，提币接口的特别说明中有详细用法。
调取接口得到返回值后给用户展示时的内容要有address和number两部分组成并分开显示，可参照火币或其他交易所的做法。
```

**接口链接**

URL测试地址：https://open.higgsnetwork.com/wallet/partner/newaddress

URL正式地址：http://交易所提供/newaddress

提交方式: POST

**headers**

```
Content-Type:   Application/Json
```

**请求参数**

所有参数不能传null

参数名 | 必填 | 类型 | 描述
---|---|---|---
partnerId | 是 | String | 合作方编号
partnerUid | 是 | String | 合作方用户唯一标识
symbol | 是 | String | 币种(要求大写如 BTC)
timestamp | 是 | Long | 是本次请求的unix时间戳（UTC），精确到毫秒
sign | 是 | String | MD5加密串防止参数篡改

**sign生成说明**

除"sign"外的所有参数按key进行字典升序排列，将排序后的参数(key=value)用&拼接得到待签名字符串，最后与得到的Key进行拼接后转小写做MD5运算(32位小写)。

例如：

```
sign = MD5(((partnerId=1&partnerUid=1&symbol=BGX&timestamp=1552639565000)+key).toLowerCase());
```

**返回说明**

接口请求成功后，会返回json串。

返回示例

```
HTTP/1.1 200 OK
{
    "code": 200,
    "message": "Completed successfully",
    "data": {
        "AddressInfo": {
            "address": "地址",
            "symbol": "币种",
            "baseToken": "主链"
        }
    }
}
or
{
    "code": 200,
    "message": "Completed successfully",
    "data": {
        "AddressInfo": {
            "address": "address|number",
            "symbol": "币种",
            "baseToken": "主链"
        }
    }
}

HTTP/1.1 200 OK
{
    code : 10000,
    msg : "Failed!",
    data :
}
```

返回字段说明

名称 | 描述
---|---
code | 返回码
msg | code描述信息
data | 返回数据结果



***2. 提币接口***
---
**版本信息**
---

版本 | 时间 | 备注
---|---|---
1.0 | 2019-03-15
1.1 | 2019-05-21 | 增加了对IOST主链的支持
**描述**
用户提币操作，请合作方做好风控审核机制（只要调用就上链了），增加沙盒模式默认不开启，沙盒模式下提币申请不上链订单为完成状态调用提币成功回调地址方便测试接口

### 特别说明
```
IOST主链的地址分为“Address”和“Number”两部分组成,Number为分配给用户的编号
在制作提币界面时IOST主链的币种应有Address和Number两个文本框可输入
调用此提币接口提取EOS和IOST主链的数字货币时addressTo参数要使用特殊格式进行提交，"Address|Number"
例：HiggsIOSTAddress|10000
个人的钱包地址是没有Number是非必填内容，此类型提币可以使用"Address|"
例：UserIOSTAddress|
```

**接口链接**

URL测试地址：https://open.higgsnetwork.com/wallet/partner/createwithdraw

URL正式地址：http://交易所提供/createwithdraw

提交方式: POST

**headers**

```
Content-Type:   Application/Json
```

**请求参数**

所有参数不能传null

参数名 | 必填 | 类型 | 描述
---|---|---|---
addressTo | 是 | String | to地址
amount | 是 | BigDecimal | 数量
fee | 是 | BigDecimal | 手续费(收取用户的手续费金额)
partnerId | 是 | String | 合作方编号
partnerOrderNo | 是 | String | 合作方订单号
sign | 是 | String | MD5加密串防止参数篡改
symbol | 是 | String | 币种(要求大写如 BTC)
timestamp | 是 | Long | 是本次请求的unix时间戳（UTC），精确到毫秒

**sign生成说明**

除"sign"外的所有参数按key进行字典升序排列，将排序后的参数(key=value)用&拼接得到待签名字符串，最后与得到的Key进行拼接后转小写做MD5运算(32位小写)。

例如：

```
sign = MD5(((addressTo=xxx&amount=121.35&fee=0.12135&partnerId=1&partnerOrderNo=w2019031518001234&symbol=BGX&timestamp=1552639565000)+key).toLowerCase());
```

**返回说明**

接口请求成功后，会返回json串。

返回示例

```
HTTP/1.1 200 OK
{
    code : 200,
    msg : "Completed successfully",
    data :
    }
}

HTTP/1.1 200 OK
{
    code : 10000,
    msg : "Failed!",
    data :
}
```

返回字段说明

名称 | 描述
---|---
code | 返回码
msg | code描述信息
data | 返回数据结果



***3. 查询开放币种***
---
**版本信息**
---

版本 | 时间 | 备注
---|---|---
1.0 | 2019-03-15
**描述**
获取HiggsNetwork对你方开放的所有币种信息

**接口链接**

URL测试地址https://open.higgsnetwork.com/wallet/partner/symbolopenlist

URL正式地址：http://交易所提供/symbolopenlist

提交方式: POST

**headers**

```
Content-Type:   Application/Json
```

**请求参数**

所有参数不能传null

参数名 | 必填 | 类型 | 描述
---|---|---|---
partnerId | 是 | String | 合作方编号
sign | 是 | String | MD5加密串防止参数篡改
timestamp | 是 | Long | 是本次请求的unix时间戳（UTC），精确到毫秒

**sign生成说明**

除"sign"外的所有参数按key进行字典升序排列，将排序后的参数(key=value)用&拼接得到待签名字符串，最后与得到的Key进行拼接后转小写做MD5运算(32位小写)。

例如：

```
sign = MD5(((partnerId=1&timestamp=1552639565000)+key).toLowerCase());
```

**返回说明**

接口请求成功后，会返回json串。

返回示例

```
HTTP/1.1 200 OK
{
    code : 200,
    msg : "Completed successfully",
    data : {"symbols":
        [
            {
                "symbol":"NEO",
                "tokenBase":"NEO",
                "contratPrecision":0,
                "depositOpen":false,
                "withdrawOpen":true
            },
            {...}
        ]
    }
}

HTTP/1.1 200 OK
{
    code : 10000,
    msg : "Failed!",
    data :
}
```

返回字段说明

名称 | 描述
---|---
code | 返回码
msg | code描述信息
data | 返回数据结果

data说明
```
{
    "symbol":"币名",
    "tokenBase":"主链名",
    "contratPrecision":合约精度,
    "depositOpen":是否开放,
    "withdrawOpen":是否开放
}
```


***4. 获得全部币种***
---
**版本信息**
---

版本 | 时间 | 备注
---|---|---
1.0 | 2019-03-15
**描述**
获取HiggsNetwork支持的所有币种信息

**接口链接**

URL测试地址：https://open.higgsnetwork.com/wallet/partner/allsymbol

URL正式地址：http://交易所提供/allsymbol

提交方式: GET

**请求参数**

参数名 | 必填 | 类型 | 描述
---|---|---|---

**sign生成说明**

**返回说明**

接口请求成功后，会返回json串。

返回示例

```
HTTP/1.1 200 OK
{
    code : 200,
    msg : "Completed successfully",
    data : {"symbols":
        [
            {
                "symbol":"NEO",
                "tokenBase":"NEO",
                "contratPrecision":0,
                "depositOpen":false,
                "withdrawOpen":true
            },
            {...}
        ]
    }
}

HTTP/1.1 200 OK
{
    code : 10000,
    msg : "Failed!",
    data :
}
```

返回字段说明

名称 | 描述
---|---
code | 返回码
msg | code描述信息
data | 返回数据结果

data说明
```
{
    "symbol":"币名",
    "tokenBase":"主链名",
    "contratPrecision":合约精度,
    "depositOpen":是否开放,
    "withdrawOpen":是否开放
}
```


***5. 检查地址接口***
---
**版本信息**
---

版本 | 时间 | 备注
---|---|---
1.0 | 2019-03-15
**描述**
已废弃

**接口链接**

URL测试地址https://open.higgsnetwork.com/wallet/partner/checkaddress

URL正式地址：http://交易所提供/checkaddress

提交方式: POST

**headers**

```
Content-Type:   Application/Json
```

**请求参数**

所有参数不能传null

参数名 | 必填 | 类型 | 描述
---|---|---|---
addrss | 是 | String | 钱包地址
partnerId | 是 | String | 合作方编号
sign | 是 | String | MD5加密串防止参数篡改
timestamp | 是 | Long | 是本次请求的unix时间戳（UTC），精确到毫秒

**sign生成说明**

除"sign"外的所有参数按key进行字典升序排列，将排序后的参数(key=value)用&拼接得到待签名字符串，最后与得到的Key进行拼接后转小写做MD5运算(32位小写)。

例如：

```
sign = MD5(((addrss=xxx&partnerId=1&timestamp=1552639565000)+key).toLowerCase());
```

**返回说明**

接口请求成功后，会返回json串。

返回示例

```
HTTP/1.1 200 OK
{
    code : 200,
    msg : "Completed successfully",
    data : {
        "status": true
    }
}
or
{
    code : 200,
    msg : "Completed successfully",
    data : {
        "status": false
    }
}

HTTP/1.1 200 OK
{
    code : 10000,
    msg : "Failed!",
    data :
}
```

返回字段说明

名称 | 描述
---|---
code | 返回码
msg | code描述信息
data | 返回数据结果



***6. 充币成功回调***
---
**版本信息**
---

版本 | 时间 | 备注
---|---|---
1.0 | 2019-03-15
**描述**
HiggsNetwork扫描到分配给合作方的地址有充值入账情况并达到主链指定确认数后，会按照合作方提供的充值回调地址把相关数据通知到合作方。

**接口链接**

URL正式地址：由合作方提供

提交方式: POST

**headers**

```
Content-Type:   Application/Json
```

**请求参数**

所有参数不能传null

参数名 | 必填 | 类型 | 描述
---|---|---|---
amount | 是 | String | 充值金额
partnerId | 是 | String | 合作方编号
symbol | 是 | String | 币种名称
toaddress | 是 | String | 收款方钱包地址(充值回调接口返回值为通过申请新地址接口分配给合作方的地址)
txid | 是 | String | 区块中的交易编号
type | 是 | String | 交易类型(充值接口返回值固定为"deposit")
sign | 是 | String | MD5加密串防止参数篡改
timestamp | 是 | Long | 是本次请求的unix时间戳（UTC），精确到毫秒

**sign生成说明**

除"sign"外的所有参数按key进行字典升序排列，将排序后的参数(key=value)用&拼接得到待签名字符串，最后与得到的Key进行拼接后转小写做MD5运算(32位小写)。

例如：

```
sign = MD5(((amount=5.0125&partnerId=1&symbol=BTC&toaddress=XXX&txid=xxx&type=deposit&timestamp=1552639565000)+key).toLowerCase());
```

**返回说明**

接口请求成功后，如处理成功或者重复通知并已处理成功请直接返回
"success"，否则我方会在2天内持续重复进行通知，请合作方进行相应处理机制防止给用户重复增加余额，TXID为区块链上交易唯一标识请做好相关处理。

返回示例

成功:
```
success
```

失败:
```
其他返回值都视为失败
```




***7. 提币成功回调***
---
**版本信息**
---

版本 | 时间 | 备注
---|---|---
1.0 | 2019-03-15
**描述**
当合作方调取提币接口后HiggsNetwork把交易打包上链后，扫描到分配给合作方的地址有提币操作以上连后，会按照合作方提供的提币回调地址把相关数据通知到合作方。

**接口链接**

URL正式地址：由合作方提供

提交方式: POST

**headers**

```
Content-Type:   Application/Json
```

**请求参数**

所有参数不能传null

参数名 | 必填 | 类型 | 描述
---|---|---|---
amount | 是 | String | 充值金额
partnerId | 是 | String | 合作方编号
partnerorderno | 是 | String | 合作方申请提币时传递过来的订单号
symbol | 是 | String | 币种名称
toaddress | 是 | String | 收款方钱包地址(提币回调接口返回值为提币申请接口传递的接收目标地址)
txid | 是 | String | 区块中的交易编号
type | 是 | String | 交易类型(提币回调接口返回值固定为"withdraw")
sign | 是 | String | MD5加密串防止参数篡改
timestamp | 是 | Long | 是本次请求的unix时间戳（UTC），精确到毫秒

**sign生成说明**

除"sign"外的所有参数按key进行字典升序排列，将排序后的参数(key=value)用&拼接得到待签名字符串，最后与得到的Key进行拼接后转小写做MD5运算(32位小写)。

例如：

```
sign = MD5(((amount=5.0125&partnerId=1&partnerorderno=xxx&symbol=BTC&toaddress=XXX&txid=xxx&type=withdraw&timestamp=1552639565000)+key).toLowerCase());
```

**返回说明**

接口请求成功后，如处理成功或者重复通知并已处理成功请直接返回
"success"，否则我方会在2天内持续重复进行通知，请合作方进行相应处理机制做好锁定币的相应处理，TXID为区块链上交易唯一标识请做好相关处理。

返回示例

成功:
```
success
```

失败:
```
其他返回值都视为失败
```




***8. 交易流水订单查询接口***
---
**版本信息**
---

版本 | 时间 | 备注
---|---|---
1.0 | 2019-05-15
**描述**
合作方按照接口要求实现接口由higgs系统调用，此接口为查询流水记录以此数据为依据将交易数据上链。
交易上链结果不会通知回调接口。

**接口链接**

URL正式地址：由合作方提供

提交方式: POST

**headers**

```
Content-Type:   Application/Json
```

**请求参数**

所有参数不能传null

参数名 | 必填 | 类型 | 描述
---|---|---|---
lastOrderNo | 是 | String | 最后一条订单的订单号
showCount | 是 | Int | 输出的订单数量
sign | 是 | String | MD5加密串防止参数篡改
timestamp | 是 | Long | 是本次请求的unix时间戳（UTC），精确到毫秒

**sign生成说明**

除"sign"外的所有参数按key进行字典升序排列，将排序后的参数(key=value)用&拼接得到待签名字符串，最后与得到的Key进行拼接后转小写做MD5运算(32位小写)。

例如：

```
sign = MD5(((lastorderno=xxx&showcount=20&timestamp=1552639565000)+key).toLowerCase());
```

**返回说明**

lastOrderNo参数为higgs读取到的最后一条数据，每次访问只需要返回lastOrderNo参数订单后面指定数量的订单信息即可，如lastOrderNo为空请从第一条订单信息开始返回。

返回示例

成功:
```
{
    "status":"success",
    "orderCount":2,
    "date":[
        {
            "orderno":"1558066091438",
            "symbol":"FISH",
            "amount":"10020.0035",
            "fromUid":"user0001",
            "toUid":"user0002"
        },
        {
            "orderno":"1558066093343",
            "symbol":"NEO",
            "amount":"10.00",
            "fromUid":"user0002",
            "toUid":"user0004"
        }
    ]
}
or
{
    "status": "success",
    "orderCount":0,
    "date": null
}
```

失败:
```
{
    "status": "failed",
    "orderCount":0,
    "date": null
}
```


返回码说明

值 | 描述 | 解决方案
---|---|---
200 | 成功 |
9999 | 未定义错误 | 请及时联系我方技术人员
10000 | 请求失败 | 请及时联系我方技术人员
10001 | IP受限 |
10002 | 参数不全 | 检测参数中是否有null值或者时间戳超过1分钟以上
10003 | 没收到任何参数 | 检测参数中是否有null值或者时间戳超过1分钟以上
10004 | sign验证未通过 | 检查签名字符串 注意按照名称排序后拼接key再转小写
10005 | 协议不支持 |
10006 | 程序异常 |
10007 | 合作方状态未开放 |
10008 | 金额异常 | 订单金额超过最小或者最大设置
10009 | 币种不正确 | 币种错误或者未开放
10010 | 订单处理中 |
10011 | 订单已存在 |
10012 | 订单已失败 |
10013 | 订单已完成 |
10014 | 手续费异常 |
10015 | 请求失败 |
10016 | 请求超时 |
