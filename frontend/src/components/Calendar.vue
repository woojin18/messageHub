<template>
  <input type="text" :id="calendarId" :class="classProps" :style="styleProps" :value="initDate">
</template>

<script>
//import jq from 'jquery'
const jq = jQuery.noConflict();

export default {
  props: {
    calendarId: {
      type: String,
      require: false
    },
    classProps: {
      type: String,
      require: false,
      default: function() {
        return 'datepicker';
      }
    },
    styleProps: {
      type: String,
      require: false,
      default: function() {
        return 'datepicker';
      }
    },
    initDate: {
      type: String,
      require: false,
      default: function() {
        return '';
      }
    },
    params: {
      type: Object,
      require: false,
      default: function() {
        return {};
      }
    }
  },
  template: '<input/>',
  mounted: function() {
    const vm = this;
    jq('#'+this.calendarId).datepicker({
      dateFormat: "yy-mm-dd"
      ,monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
      ,monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
      ,dayNames: ['일', '월', '화', '수', '목', '금', '토']
      ,dayNamesShort: ['일', '월', '화', '수', '목', '금', '토']
      ,dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
      ,changeMonth: true // 월선택 select box 표시 (기본은 false)
      ,changeYear: true  // 년선택 selectbox 표시 (기본은 false)
      ,showMonthAfterYear: true // 다음년도 월 보이기
      ,showOtherMonths: true // 다른 월 달력에 보이기
      ,selectOtherMonths: true // 다른 월 달력에 보이는거 클릭 가능하게 하기
      ,onSelect: function(d){vm.$emit('update-date',d, vm.params)}
    });
  },
  beforeDestroy: function(){jq('#'+this.calendarId).datepicker('hide').datepicker('destroy')}
};
</script>