<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="厂家名称" prop="makersName">
        <el-input
          v-model="queryParams.makersName"
          placeholder="请输入厂家名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关键字" prop="makersGJZ">
        <el-input
          v-model="queryParams.makersGJZ"
          placeholder="请输入关键字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="厂家电话" prop="makersPhone">
        <el-input
          v-model="queryParams.makersPhone"
          placeholder="请输入厂家电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="可用状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          placeholder="请选择日期"
          value-format="yyyy-MM-dd"
          clearable
          size="small"
        />
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['his:items:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['his:items:upd']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['his:items:remove']"
        >删除</el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['system:post:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="makersList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="厂家ID" align="center" prop="makersId" />
      <el-table-column label="厂家名称" align="center" prop="makersName" />
      <el-table-column label="厂家编码" align="center" prop="makersCode" />
      <el-table-column label="联系人" align="center" prop="makersLeader" />
      <el-table-column label="电话" align="center" prop="makersPhone" />
      <el-table-column label="地址" align="center" prop="makersAddress" />
      <el-table-column label="关键字" align="center" prop="makersGJZ" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['his:items:upd']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['his:items:remove']"
          >删除</el-button>
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

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="厂家名称" prop="makersName">
          <el-input v-model="form.makersName" placeholder="请输入厂家名称" />
        </el-form-item>
        <el-form-item label="厂家编码" prop="makersCode">
          <el-input v-model="form.makersCode" placeholder="请输入厂家关键字" />
        </el-form-item>
        <el-form-item label="联系人" prop="makersLeader">
          <el-input v-model="form.makersLeader" placeholder="请输入厂家联系人"/>
        </el-form-item>
        <el-form-item label="电话" prop="makersPhone">
          <el-input v-model="form.makersPhone" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="关键字" prop="makersGJZ">
          <el-input v-model="form.makersGJZ" placeholder="请输入厂家关键字" />
        </el-form-item>
        <el-form-item label="地址" prop="makersAddress">
          <el-input v-model="form.makersAddress" placeholder="请输入厂家地址" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryMakers, getMakers, delMakers, addMakers, updateMakers } from "@/api/drugs/makers";

export default {
  name: "Makers",
  dicts: ['sys_normal_disable'],
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
      // 厂家表格数据
      makersList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        makersGJZ: undefined,
        makersName: undefined,
        makersPhone: undefined,
        status: undefined,
        createTime:undefined,
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
    /** 查询检查费用设置列表 */
    getList() {
      this.loading = true;
      console.log(this.queryParams)
      queryMakers(this.queryParams).then(response => {
        this.makersList = response.rows;
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
        makersPhone:undefined,
        makersAddress:undefined,
        makersLeader:undefined,
        makersCode: undefined,
        makersName: undefined,
        makersGJZ: undefined,
        status: "0",
        remark: undefined
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
      this.ids = selection.map(item => item.makersId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加厂家信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const makersId = row.makersId || this.ids
      getMakers(makersId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改厂家信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.makersId !== undefined) {
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
      const makersIds = row.makersId || this.ids;
      this.$modal.confirm('是否确认删除厂家ID为"' + makersIds + '"的数据项？').then(function() {
        return delMakers(makersIds);
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

