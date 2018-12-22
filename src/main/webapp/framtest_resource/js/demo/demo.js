(function () {
    var $ajaxTest = $('#ajaxTest');
    var $xmlhttpPOST = $('#xmlhttpPOST');
    var $xmlhttpGET = $('#xmlhttpGET');
    var $toDemo = $('#toDemo');
    var $inputsome = $('#inputsome');
    var $simple = $('#simple');


    $toDemo.on('click',function(){
        console.log("说点什么");
        window.open("/demo/toDemo");
    });

    $inputsome.on('input',function(){
        var time = new Date().getTime();
        console.log("start"+time);
        setTimeout(function(){
            $.ajax({
                url:"/demoRestfull/returnValue",
                type:"POST",
                contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                dataType:'json',
                data:{
                    timeStamp:time
                },
                success:function (data) {
                    console.log("end"+ data.timeStamp);
                }
            },Math.random()*100000);
        });
    });



    $ajaxTest.on('click',function (e) {
        console.log( e.target.value);
        $.ajax({
            url:"/demoRestfull/returnCacheMap",
            type:"POST",
            dataType:'json',
            data:{
                name:"小军",
                id:"6",
            },
            dataFilter:function(data){
                console.log("dataFilter");
            },
            beforeSend:function (xmlHttpRequest) {
              console.log("beforeSend");
            },
            success:function (data) {
              alert("success");
            },
            error:function(xmlHttpRequest){
                console.log("error");
            },
            complete:function (xmlHttpRequest) {
               console.log("complete");
            }
        });
    })
    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function(){
        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200){
            var data = xmlHttpRequest.responseText;
            var dataJson = JSON.parse(data);
            console.log("success");
        }
    }
    xmlHttpRequest.open("POST","/demoRestfull/returnCacheMap",true);
    xmlHttpRequest.send("name=小辉&id=2");

    $xmlhttpGET.on('click',function(e){
        console.log( e.target.value);
        var xmlHttpRequest = new XMLHttpRequest();
        xmlHttpRequest.onreadystatechange = function(){
            var data1 = xmlHttpRequest.response;
            if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200){
                //xmlHttpRequest.responseText 获取返回的数据
                var data = xmlHttpRequest.response;
                console.log(data.name);
            }
        };
        xmlHttpRequest.open("GET","/demoRestfull/returnCacheMap?name=小辉&id=2",true);
        //超时2秒
        xmlHttpRequest.timeout = 2000;
        xmlHttpRequest.ontimeout = function () {
            console.log("超时了");
        }
        xmlHttpRequest.responseType = "json";
        xmlHttpRequest.send();
    })

    $xmlhttpPOST.on('click',function (e) {
       console.log(e.target.value);
       var xmlHttpRequest = new XMLHttpRequest();
        xmlHttpRequest.onreadystatechange = function(){
            var data1 = xmlHttpRequest.responseText;
            console.log(xmlHttpRequest.readyState);
            if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200){
                //xmlHttpRequest.responseText 获取返回数据的字符串形式
                var data = xmlHttpRequest.responseText;
                var contentType = xmlHttpRequest.getResponseHeader("Content-type");
                var heads = xmlHttpRequest.getAllResponseHeaders();
                console.log("success");
            }
        };
       xmlHttpRequest.open("POST","/demoRestfull/returnCacheMap",true);
       xmlHttpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
       xmlHttpRequest.send("name=小莉&id=3");
    });
})();;