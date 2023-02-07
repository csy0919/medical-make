<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="供应商" prop="vendorName">
        <el-select v-model="queryParams.vendorName" placeholder="请选择供应商" clearable>
          <el-option
            v-for="dict in vendorList"
            :label="dict.vendorName"
            :value="dict.vendorName"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="申请人" prop="purchaseApplicant">
        <el-input
          v-model="queryParams.purchaseApplicant"
          placeholder="请输入申请人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单据状态" prop="purchaseStatus">
        <el-select v-model="queryParams.purchaseStatus" placeholder="单据状态" clearable>
          <el-option
            v-for="dict in dict.type.drugs_purchase_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseList" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="单据ID" align="center" :show-overflow-tooltip="true" width="200">
        <template slot-scope="scope">
          <router-link :to="'/drugs/purchase/update' " class="link-type">
<!--            + scope.row.purchaseId-->
            <span>{{ scope.row.purchaseId}}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="供应商" align="center" prop="vendorName" />
      <el-table-column label="采购批发总额" align="center" prop="purchaseMoney" />
      <el-table-column label="入库状态" align="center" prop="purchaseStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.drugs_purchase_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="purchaseApplicant" />
      <el-table-column label="入库操作人" align="center" prop="purchaseOperate" />
      <el-table-column label="入库时间" align="center" prop="purchaseTime" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { queryPurchase, getMakers, delMakers, addMakers, updateMakers } from "@/api/drugs/purchase";
import {queryVendors} from "@/api/drugs/vendor";

export default {
  name: "Purchase",
  dicts: ['drugs_purchase_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购入库数据列表
      purchaseList: [],
      // 供应商字典
      vendorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        vendorName: undefined,
        purchaseApplicant: undefined,
        purchaseStatus: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        makersAddress:[
          { required: true, message: "厂家地址不能为空", trigger: "blur"}
        ],
        makersName:[
          { required: true, message: "厂家名称不能为空", trigger: "blur"}
        ],
        makersPhone: [
          { required: true, message: "电话不能为空", trigger: "blur" }
        ],
        makersGJZ: [
          { required: true, message: "厂家关键字不能为空", trigger: "blur" }
        ],

      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询采购入库信息列表 */
    getList() {
      this.loading = true;
      queryVendors().then(response => {
        this.vendorList = response.rows;
      });
      queryPurchase(this.queryParams).then(response => {
        this.purchaseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        purchaseId:undefined,
        purchaseMoney:undefined,
        purchaseApplicant:undefined,
        purchaseOperate: undefined,
        purchaseName: undefined,
        purchaseTime: undefined,
        purchaseStatus: "0",
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.purchaseId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push('/drugs/purchase/date')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const purchaseId = row.purchaseId || this.ids
      getMakers(purchaseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改厂家信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.purchaseId !== undefined) {
            updateMakers(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMakers(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const purchaseIds = row.purchaseId || this.ids;
      this.$modal.confirm('是否确认删除厂家ID为"' + purchaseIds + '"的数据项？').then(function() {
        return delMakers(purchaseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    //   handleExport() {
    //     this.download('system/post/export', {
    //       ...this.queryParams
    //     }, `post_${new Date().getTime()}.xlsx`)
    //   }
  }
};
</script>

<style>
.el-dialog__title{
  display: flex;
  justify-content: center;
}
</style>

