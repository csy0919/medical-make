<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="药品名称" prop="drugName">
        <el-input
          v-model="queryParams.drugName"
          placeholder="请输入药品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="药品编码" prop="drugCode">
        <el-input
          v-model="queryParams.drugCode"
          placeholder="请输入药品编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="药品类型" prop="drugType">
        <el-select v-model="queryParams.drugType" placeholder="药品类型" clearable>
          <el-option
            v-for="dict in dict.type.drug_drug_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="生产厂家" prop="makersId">
        <el-select v-model="queryParams.makersId" placeholder="生产厂家" clearable>
          <el-option
            v-for="dict in makersList"
            :key="dict.makersId"
            :label="dict.makersName"
            :value="dict.makersId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处方类型" prop="prescriptionType">
        <el-select v-model="queryParams.prescriptionType" placeholder="处方类型" clearable>
          <el-option
            v-for="dict in dict.type.drug_prescription_type"
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
          v-hasPermi="['drugs:drug:add']"
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
          v-hasPermi="['drugs:drug:upd']"
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
          v-hasPermi="['drugs:drug:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="openImportTable"
          v-hasPermi="['drugs:drug:import']"
        >导入删除药品</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="drugList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="药品ID" align="center" prop="drugId" />
      <el-table-column label="药品名称" align="center" prop="drugName" />
      <el-table-column label="药品编码" align="center" prop="drugCode" />
      <el-table-column label="生产厂家" align="center" prop="makersId" >
        <template slot-scope="scope">
          <template v-for="(item, index) in makersList" >
            <template v-if="scope.row.makersId == item.makersId">
            <span
              :key="item.makersId"
              :index="index"
            >{{ item.makersName }}</span>
            </template>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="药品类型" align="center" prop="drugType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.drug_drug_type" :value="scope.row.drugType"/>
        </template>
      </el-table-column>
      <el-table-column label="处方类型" align="center" prop="prescriptionType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.drug_prescription_type" :value="scope.row.prescriptionType"/>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="drugUnit" />
      <el-table-column label="处方价格" align="center" prop="prescriptionPrice" />
      <el-table-column label="库存量" align="center" prop="drugInventory" />
      <el-table-column label="预警值" align="center" prop="drugAlert" />
      <el-table-column label="换算量" align="center" prop="drugConversion" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
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

    <import-table ref="import" />

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="药品名称" prop="drugName">
          <el-input v-model="form.drugName" placeholder="请输入药品名称" />
        </el-form-item>
        <el-form-item label="药品编码" prop="drugCode">
          <el-input v-model="form.drugCode" placeholder="请输入药品编码" />
        </el-form-item>
        <el-form-item label="单位" prop="drugUnit">
          <el-input v-model="form.drugUnit" placeholder="请输入药品单位"/>
        </el-form-item>
        <el-form-item label="换算量" prop="drugConversion">
          <el-input v-model="form.drugConversion" placeholder="请输入换算量"/>
        </el-form-item>
        <el-form-item label="处方价格" prop="prescriptionPrice">
          <el-input-number v-model="form.prescriptionPrice"  :min="0"  placeholder="请输入处方价格"/>
        </el-form-item>
        <el-form-item label="库存量" prop="drugInventory">
          <el-input v-model="form.drugInventory" placeholder="请输入库存量" />
        </el-form-item>
        <el-form-item label="预警值" prop="drugAlert">
          <el-input-number v-model="form.drugAlert"  :min="0" placeholder="请输入预警值"/>
        </el-form-item>
        <el-form-item label="药品类型" prop="drugType">
          <el-select v-model="form.drugType" placeholder="药品类型">
            <el-option
              v-for="dict in dict.type.drug_drug_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
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
        <el-form-item label="处方类型" prop="prescriptionType" >
          <el-select v-model="form.prescriptionType" placeholder="处方类型">
            <el-option
              v-for="dict in dict.type.drug_prescription_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="生产厂家" prop="makersId" >
          <el-select v-model="form.makersId" placeholder="生产厂家">
            <el-option
              v-for="item in makersList"
              :key="item.makersId"
              :label="item.makersName"
              :value="item.makersId"
            />
          </el-select>
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
import { queryDrug, getDrug, delDrug, addDrug, updateDrug, queryMakers} from "@/api/drugs/drug";
import importTable from "@/views/drugs/drug/importTable";



export default {
  name: "Makers",
  components: { importTable },
  dicts: ['sys_normal_disable','drug_prescription_type','drug_drug_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 唯一标识符
      uniqueId: "",
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 药品表格数据
      drugList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //厂家ID字典
      makersList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        makersId: undefined,
        drugName: undefined,
        drugCode: undefined,
        status: undefined,
        prescriptionType:undefined,
        drugType:undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        drugName:[
          { required: true, message: "药品名称不能为空", trigger: "blur"}
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
      queryMakers(this.queryParams).then(res => {
        this.makersList = res.rows;
      })
      queryDrug(this.queryParams).then(response => {
        this.drugList = response.rows;
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
        drugName: undefined,
        makersId: undefined,
        drugCode: undefined,
        prescriptionType:undefined,
        drugType:undefined,
        drugUnit:undefined,
        drugConversion:undefined,
        prescriptionPrice:undefined,
        drugInventory:undefined,
        drugAlert:undefined,
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
      this.ids = selection.map(item => item.drugId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增药品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const drugId = row.drugId || this.ids
      getDrug(drugId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改药品信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.drugId !== undefined) {
            updateDrug(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDrug(this.form).then(response => {
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
      const drugIds = row.drugId || this.ids;
      this.$modal.confirm('是否确认删除药品ID为"' + drugIds + '"的数据项？').then(function() {
        return delDrug(drugIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导入删除药品按钮操作 */
    openImportTable() {
      this.$refs.import.show(this.makersList);
    }
  }
};
</script>

<style>
  .el-dialog__title{
    display: flex;
    justify-content: center;
  }
</style>

