<template>
  <div v-if="noticePopupOpen">
    <!-- <slot /> -->
    TEST
  </div>
</template>

<script>
export default {
  name: 'noticePopup',
  props: {
    noticePopupOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '공지사항';
      }
    },
  },
  data() {
    return {
      windowRef: null,
    }
  },
  watch: {
    noticePopupOpen(newOpen) {
      if(newOpen) {
        this.fnOpenPortal();
      } else {
        this.fnClosePortal();
      }
    }
  },
  mounted() {
    if(this.noticePopupOpen) {
      this.fnOpenPortal();
    }
  },
  beforeDestroy() {
    if (this.windowRef) {
      this.fnClosePortal();
    }
  },
  methods: {
    fnOpenPortal() {
      this.windowRef = window.open("", "", "width=600,height=400,left=200,top=200");
      console.log(this.windowRef);
      this.windowRef.addEventListener('beforeunload', this.closePortal);
      this.windowRef.document.body.appendChild(this.$el);
    },
    fnClosePortal() {
      if(this.windowRef) {
        this.windowRef.close();
        this.windowRef = null;
        this.$emit('update:noticePopupOpen', false);
      }
    }
  },
}
</script>