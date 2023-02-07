<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8" >
     <el-col></el-col>
     <el-col :span="1.5">
       <el-button
         type="primary"
         icon="el-icon-plus"
         size="mini"
         @click="handleAdd"
         v-hasPermi="['drugs:purchase:add']"
       >新增采购</el-button>
     </el-col>
     <el-col :span="1.5">
       <el-button
         type="success"
         icon="el-icon-edit"
         size="mini"
         :disabled="multiple"
         @click="handleUpdate"
         v-hasPermi="['drugs:purchase:audit']"
       >提交审核</el-button>
     </el-col>
     <el-col :span="1.5">
       <el-button
         type="danger"
         icon="el-icon-delete"
         size="mini"
         :disabled="multiple"
         @click="handleDelete"
         v-hasPermi="['drugs:purchase:remove']"
       >作废</el-button>
     </el-col>
     <el-col :span="1.5">
       <el-button
         type="success"
         icon="el-icon-edit"
         size="mini"
         :disabled="multiple"
         @click="handleUpdate"
         v-hasPermi="['drugs:purchase:storage']"
       >提交入库</el-button>
     </el-col>
   </el-row>
  </div>
</template>

<script>
export default {
  name: "date"
}
</script>

<style scoped>

</style>
