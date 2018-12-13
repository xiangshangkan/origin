(function () {


    /**
    * post方式异步提交
    * @author      zhouhui
    * @param
    * @date        2018/12/12 14:37
    */
    function postjson(url,datas,callback,errback){
        try{
            $.ajax({
                url:'',
                type:'POST',
                dataType:'json',
                contentType:"application/json;utf-8",
                data:datas,
                success:function(result){
                    callback();
                },
                error:function(error){
                    if (errback){
                        errback();
                    } else {
                        console.log(error.message);
                    }
                }
            });
        }catch (e){
            console.log(e.message)
        }
    }

    /**
    * 获取简单表单参数
    * @author      zhouhui
    * @param
    * @date        2018/12/12 14:37
    */
    function getDatas(from){
        var formData = {};
        _.forEach($(from).serializeArray() || [],function(item){
            formData[item.name] = item.value;
        });
        return formData;
    }
    //自动补全
    function autoJg(el, name, num) {
        el.onfocus = function () {
        };
        var $name = $(el).siblings(name);
        var $num = $(el).siblings(num);
        $(el).autocomplete({
            source: function (request, response) {
                var reqItem = request.term;
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: "/jg/data/getJgByKey",
                    data: { key: reqItem },
                    success: function (result) {
                        var list;
                        if (result && result.success) {
                            list = result.data.data;
                        } else {
                            list = [];
                        }
                        var items = [];
                        for (var i = 0, l = list.length; i < l; i++) {
                            items.push(
                                {
                                    label: list[i].deptName || '',
                                    value: list[i].deptName || '',
                                    data: list[i]
                                }
                            );
                        }
                        if (items.length === 0) {
                            items.push({
                                label: '无数据',
                                value: '',
                                data: {}
                            });
                        }
                        response(items);
                    }
                });
            },
            select: function (event, ui) {
                var data = ui && ui.item && ui.item.data;
                data = data || {};
                $name.val(data.deptName || '');
                $num.val(data.oldShopId || '');
            },
            close: function () {
                if ($(el).val() !== $name.val()) {
                    $(el).val('');
                    $name.val('');
                    $num.val('');
                }
            }
        });
    }


    /**
    * $.serializeArray() 根据form标签name属性产生的 json 数组数据  单个对象提交与多个对象都可转换
    * 常规的：
    * [{name:'userId',value:'1'},{name:'userName',value:'jack'}]    转成    {'userId':'1','userName':'jack'}
    * [{name:'userId',value:'1'},{name:'userName',value:'rose'},{name:'userId',value:'2'},{name:'userName',value:'jack'}] 转成 [{"userId": "1", "userName": "rose"},{"userId": "2", "userName": "jack"}]
    * 非常规的
    * [{name:'userId',value:'1'},{name:'userId',value:'2'},{name:'userName',value:'jack'}] 转成[{"userId": "1", "userName": ""},{"userId": "2", "userName": "jack"}]
    * @author      zhouhui
    * @param       detailArray  $.serializeArray()生成的json数组
    * @date        2018/11/2 20:05
    */
    function serializeArrayToJson( detailArray){
        //获取属性名数组（去重）
        var names = [];
        for(var i = 0; i< detailArray.length ;i++){
            if(names.indexOf(detailArray[i].name) === -1){
                names.push(detailArray[i].name);
            }
        }
        //先转换成{"userId": ["1","2"], "userName": ["","jack"]}这种形式
        var detail = {};
        for(var j = 0,k = 0; j < detailArray.length; j++ ,k++){
            if(k === names.length){
                //一个对象转换完成，开始下一个对象的转换
                k = 0;
            }
            var name, value ;
            if(detailArray[j].name !== names[k]){
                //说明该对象name[i]对应的属性值为空
                name = names[k];
                value = ''
                j--;
            }else{
                name = detailArray[j].name;
                value = detailArray[j].value;
            }

            if(detail[name] === undefined || detail[name] === 'undefined'){
                //若是单对象或是对象数组的首对象，格式转换过程 {} ---->  {"userId":"1"}  ----> {"userId":"1","userName":""}
                detail[name] = value || '';
            }else{
                //若是多对象，此时detail已是{"userId":"1","userName":""}的形式，需要转换成{"userId": ["1","2"], "userName": ["","jack"]}形式
                if($.isArray(detail[name])){
                    //判断detail中是否已存在对应key值的数组，转换过程{"userId": ["1","2"], "userName": ["","jack"]} ---->{"userId": ["1","2",......], "userName": ["","jack",......]}
                    detail[name].push(value || '');
                }else {
                    //否则在detail中创建数组，格式转换过程 {"userId":"1","userName":""} ---->{"userId": ["1","2"], "userName": ""} ----> {"userId": ["1","2"], "userName": ["","jack"]}
                    detail[name] = [detail[name],value];
                }
            }
        }

        //计算json内部数组的最大长度
        var count = 0;
        for (var item in detail){
            //判断对应key值对应的是单值还是数组，若是数组求长度，若是单值，长度就为1
            var temp = $.isArray(detail[item]) ? detail[item].length : 1;
            //记录当前已遍历的最长数组
            count = temp > count ? temp : count;
        }

        // 再转成[{"id": "12", "name": "aaa", "pwd":"pwd1"},{"id": "14", "name": "bb", "pwd":"pwd2"}]的形式
        var detail2 = new Array();
        if(count > 1) {
            for(var i = 0; i < count; i++){
                var jsonObj = {};
                for(var item in detail) {
                    jsonObj[item] = detail[item][i] || '';
                }
                //获得json数组
                detail2.push(jsonObj);
            }
            return detail2;
        }else{
            //所有key对应的都是单值(或是参数为空)，则本身就满足格式，无需再转
            detail2.push(detail)
            return detail2;
        }
    }

    /**
    * serializeArray数组转json格式(对单对象且有子实体成员有用,子对象的name值的格式（主对象中变量名.子对象成员名）)
    * @author      zhouhui
    * @param     未完成
    * @date        2018/10/10 21:57
    */
    function serializeArrayToJsonComponent(detailArray){
        //主对象成员数组（不包括子实体成员）
        var mainArray = new Array();
        //生成的标准格式的
        var mainJson,
            subJson;
        var subArrays = {}
        var mainNames = new Array();
        var mainName,subName;
        //先将serializeArray数组拆分成主对象Array数组和子对象Array数组
        for(var i = 0; i<detailArray.length; i++){
            var jsonObj = {};
            //属性名是 （主对象中子实体成员名.子实体成员的成员名） 的格式，属于子实体成员的成员，按子实体成员名分类
            if(detailArray[i].name.indexOf('.') !== -1){
                mainName = detailArray[i].name.subString(0,index);
                subName = detailArray[i].name.substring(index+1);
                jsonObj.name = subName;
                jsonObj.value = detailArray[i].value;
                if(subArrays[mainName] === undefined ||subArrays[mainName] === 'undefined'){
                    subArrays[mainName] = new Array();
                }
                subArrays[mainName].push(jsonObj);
                if(mainNames.indexOf(mainName) === -1){
                    mainNames.push(mainName);
                }
            }else{
                mainArray.push(detailArray[i]);
            }
        }
        mainJson = serializeArrayToJson(mainArray)[0];
        if(mainNames.length > 0){
            for(item in mainNames){
                subJson = serializeArrayToJson(subArrays[item]);
                mainJson[item] = subJson;
            }
        }
        return mainJson;
    }

})();