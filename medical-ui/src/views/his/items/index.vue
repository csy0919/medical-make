<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="itemsName">
        <el-input
          v-model="queryParams.itemsName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关键字" prop="itemsGJZ">
        <el-input
          v-model="queryParams.itemsGJZ"
          placeholder="请输入关键字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目类型" prop="itemsType">
        <el-select v-model="queryParams.itemsType" placeholder="项目类型" clearable>
          <el-option
            v-for="dict in dict.type.items_oper_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system:post:add']"
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
          v-hasPermi="['system:post:edit']"
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
          v-hasPermi="['system:post:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:post:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="项目费用ID" align="center" prop="itemsId" />
      <el-table-column label="项目名称" align="center" prop="itemsName" />
      <el-table-column label="关键字" align="center" prop="itemsGJZ" />
      <el-table-column label="项目单价" align="center" prop="itemsPrice" />
      <el-table-column label="项目成本" align="center" prop="itemsCost" />
      <el-table-column label="单位" align="center" prop="itemsUnit" />
      <el-table-column label="类别" align="center" prop="itemsType" >
        <template slot-scope="scope">
          <dict-tag  :options="dict.type.items_oper_type" :value="scope.row.itemsType"/>
        </template>
      </el-table-column>
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
        <el-form-item label="项目类型" prop="itemsType">
          <el-select v-model="form.itemsType">
            <el-option
              v-for="dict in dict.type.items_oper_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="项目名称" prop="itemsName">
          <el-input v-model="form.itemsName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="关键字" prop="itemsGJZ">
          <el-input v-model="form.itemsGJZ" placeholder="请输入项目关键字" />
        </el-form-item>
        <el-form-item label="项目价格" prop="itemsPrice">
          <el-input v-model="form.itemsPrice" placeholder="请输入项目价格"/>
        </el-form-item>
        <el-form-item label="项目成本" prop="itemsCost">
          <el-input v-model="form.itemsCost" placeholder="请输入项目成本"/>
        </el-form-item>
        <el-form-item label="项目单位" prop="itemsUnit">
          <el-input v-model="form.itemsUnit" placeholder="请输入项目单位" />
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
import { listItems, getItems, delItems, addItems, updateItems } from "@/api/his/items";

export default {
  name: "Items",
  dicts: ['sys_normal_disable','items_oper_type'],
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
      // 项目费用表格数据
      itemsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemsGJZ: undefined,
        itemsName: undefined,
        itemsType: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemsPrice:[
          { required: true, message: "项目价格不能为空", trigger: "blur"}
        ],
        itemsCost:[
          { required: true, message: "项目成本不能为空", trigger: "blur"}
        ],
        itemsName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        itemsType: [
          { required: true, message: "项目类型不能为空", trigger: "blur" }
        ],
        itemsGJZ: [
          { required: true, message: "项目关键字不能为空", trigger: "blur" }
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
      listItems(this.queryParams).then(response => {
        this.itemsList = response.rows;
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
        itemsPrice:undefined,
        itemsCost:undefined,
        itemsType: undefined,
        itemsName: undefined,
        itemsGJZ: undefined,
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
      this.ids = selection.map(item => item.itemsId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加检查项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const itemsId = row.itemsId || this.ids
      getItems(itemsId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改检查项目";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.itemsId !== undefined) {
            updateItems(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItems(this.form).then(response => {
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
      const itemsIds = row.itemsId || this.ids;
      this.$modal.confirm('是否确认删除项目费用ID为"' + itemsIds + '"的数据项？').then(function() {
        return delItems(itemsIds);
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

