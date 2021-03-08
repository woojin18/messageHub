<template>
    <select @change="fnSelectValue" v-model="selected">
        <option disabled value="">Please select one</option>
        <option value="10">10개씩 보기</option>
        <option value="20">20개씩 보기</option>
        <option value="30">30개씩 보기</option>
    </select>
</template>

<script>
export default {
  name: 'SelectLayer',
   data: () => ({
        selected: 10,
    }),
  methods: {
    fnSelectValue() {
      this.$emit('fnSelected', this.selected);
    },
  }
}
</script>
