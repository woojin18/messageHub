import axios from 'axios';
import Vue from 'vue';

axios.defaults.baseURL = 'http://localhost:7070';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

Vue.prototype.$fnExportExcelToSvc = (colLabels, colIds, numColIds, sheetTitle, excelFileName, contextPath, fieldSearchParamArray, figureColIds) => {
    var frm = document.createElement("form");
    frm.name ="frm";
    frm.method = "POST";
    frm.action = axios.defaults.baseURL+contextPath;
    frm.target = "_self";

    var sheetTitleObj = document.createElement("input");
    sheetTitleObj.type = "hidden";
    sheetTitleObj.name = "sheetTitle";
    sheetTitleObj.value = sheetTitle;
    frm.insertBefore(sheetTitleObj, null);

    var excelFileNameObj = document.createElement("input");
    excelFileNameObj.type="hidden";
    excelFileNameObj.name="excelFileName";
    excelFileNameObj.value=excelFileName;
    frm.insertBefore(excelFileNameObj, null);
    
    for(var i=0;i<colLabels.length;i++) {
      var colLabelsObj=document.createElement("input");
      colLabelsObj.type="hidden"; 
      colLabelsObj.name="colLabels"; 
      colLabelsObj.value=colLabels[i];
      frm.insertBefore(colLabelsObj, null);
    }

    for(var i=0;i<colIds.length;i++) {
      var colIdsObj=document.createElement("input");
      colIdsObj.type="hidden";
      colIdsObj.name="colIds";
      colIdsObj.value=colIds[i];
      frm.insertBefore(colIdsObj, null);
    }

    for(var i=0;i<numColIds.length;i++) {
      var numColIdsObj=document.createElement("input");
      numColIdsObj.type="hidden";
      numColIdsObj.name="numColIds";
      numColIdsObj.value=numColIds[i];
      frm.insertBefore(numColIdsObj, null);
    }
    
    if(figureColIds!=null && figureColIds.length>0) {
      for(var i=0;i<figureColIds.length;i++) {
        var figureColIdsObj=document.createElement("input");
        figureColIdsObj.type="hidden";
        figureColIdsObj.name="figureColIds";
        figureColIdsObj.value=figureColIds[i];
        frm.insertBefore(figureColIdsObj, null);
      }
    }
    
    for(var i = 0 ; i < fieldSearchParamArray.length ; i++){
      var fieldSearchParamArrayObj = document.createElement("input");
      fieldSearchParamArrayObj.type="hidden";
      fieldSearchParamArrayObj.name=fieldSearchParamArray[i];
      if($('input[name="'+fieldSearchParamArray[i]+'"]').attr("type")=='radio') {
        fieldSearchParamArrayObj.value = $(':radio[name="'+fieldSearchParamArray[i]+'"]:checked').val();
      } else if ($('input[name="'+fieldSearchParamArray[i]+'"]').attr("type")=='checkbox') {
        var checkboxArr = new Array();
        $(':checkbox[name="'+fieldSearchParamArray[i]+'"]:checked').each(function(){
          var value = $(this).val();
          checkboxArr.push(value);
        });
        fieldSearchParamArrayObj.value = checkboxArr;
      } else {
        if($('#' + fieldSearchParamArray[i]).val() != undefined){
          fieldSearchParamArrayObj.value=$('#' + fieldSearchParamArray[i]).val();
        }
      }
      frm.insertBefore(fieldSearchParamArrayObj, null);
    }
    document.body.appendChild (frm);
    frm.submit();
  }