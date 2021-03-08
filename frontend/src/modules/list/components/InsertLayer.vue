<template>
  <div v-if="layerView" class="layerPopup" @click.self="fnCloseLayer">
    <div class="layerPopup__dialog">
      <header class="layerPopup__header">
        <span>{{title}}</span>
        <span class="btnClose" @click="fnCloseLayer"><i class="far fa-times-circle"></i></span>
      </header>
      <div class="layerPopup__body">
        <table>
          <tr>
            <td>COL1</td>
            <td><input type="text" id="col1" /></td>
          </tr>
           <tr>
            <td>COL2</td>
            <td><input type="text" id="col2" /></td>
          </tr>
           <tr>
            <td>COL3</td>
            <td><input type="text" id="col3" /></td>
          </tr>
           <tr>
            <td>COL4</td>
            <td><input type="text" id="col4" /></td>
          </tr>
           <tr>
            <td>COL5</td>
            <td><input type="text" id="col5" /></td>
          </tr>
           <tr>
            <td>COL6</td>
            <td><input type="text" id="col6" /></td>
          </tr>
        </table>
      </div>
      <footer class="layerPopup__footer">
        <button @click="fnInsertList">저장</button>
        <button @click="fnCloseLayer">닫기</button>
      </footer>
    </div>
  </div>
</template>

<script>
import listApi from "@/modules/list/service/api"

export default {
  name: 'InsertLayer',
  props: {
    layerView: {
      type: Boolean,
      require: true,
      default: false
    },
    title: {
      type: String,
      require: false
    }
  },
  methods: {
    fnCloseLayer: function() {
      this.$emit('update:layerView', false);
    },

    fnInsertList: function() {
      var params = {
          "col1": $("#col1").val(),
          "col2": $("#col2").val(),
          "col3": $("#col3").val(),
          "col4": $("#col4").val(),
          "col5": $("#col5").val(),
          "col6": $("#col6").val()
        };

      listApi.insertList(params).then(response => {
          alert("success");
          console.log(response);
        var result = response.data;
        if(result.success) {
          alert("리스트가 등록되었습니다.");
        } else {
            alert("리스트 등록에 실패하였습니다.");
        }
        this.$parent.fnSearch();
        this.fnCloseLayer();
      });
    }
  }
}
</script>

<style lang="scss">
$module: 'layerPopup';
.#{$module} {
  background-color: rgba(0,0,0,0.7);
  top:0; right:0; bottom:0; left: 0;
  position: fixed; overflow: auto;
  margin: 0;
  z-index: 999;

  &__dialog {
    border: 1px solid #000000;
    background-color: white;
    position: fixed;
    z-index: 9999;
    margin: 0;
    width: 500px; height: 500px;
    top: 50%; left: 50%;
    margin-left: -250px;
    margin-top: -250px;
  }

  &__header {
    height: 40px;
    font-size: 28px;
    font-weight: bold;
    line-height: 28px;
    padding: 16px 16px 0 25px;
    background-color: grey;
    color: white;
    position: relative;
  }
  &__header .btnClose {
    position: absolute;
    right: 15px;
  }

  &__body {
    padding: 25px;
    overflow-y: auto;
    padding: 0px;
    margin: 20px;
  }
  &__body table {
    border: 1px solid black;
    border-collapse: collapse;
    width: 100%;
  }
  &__body th, td {
    border: 1px solid black;
  }
  &__body input[type=text] {
    width: 80%;
    border: 3px solid #555;
    margin: 3px;
  }

  &__footer {
    padding: 0px;
    margin:20px;
    text-align: center;
  }
}
</style>