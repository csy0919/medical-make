<template>
  <!-- 导入已删除药品 -->
  <el-dialog title="导入已删除药品" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="drugDelList" @selection-change="handleSelectionChange" height="260px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="drugId" label="药品id" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="drugName" label="药品名称" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="drugCode" label="药品编码" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="makersId" label="生产厂家" :show-overflow-tooltip="true">
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
        <el-table-column prop="createTime" label="删除时间"></el-table-column>
        <el-table-column prop="createBy" label="删除人"></el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleImportTable">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listDelDrug, importDrug } from "@/api/drugs/removeDrug";
export default {
  data() {
    return {
      // 遮罩层
      visible: false,
      //厂家数组
      makersList:[],
      // 选中数组值
      drugIds: [],
      // 总条数
      total: 0,
      // 药品数据
      drugDelList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        drugName: undefined,
        drugCode: undefined
      }
    };
  },
  methods: {
    // 显示弹框
    show(data) {
      this.makersList=data;
      this.visible = true;
      this.getList();

    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.drugIds = selection.map(item => item.drugId);

    },
    // 查询表数据
    getList() {
      listDelDrug(this.queryParams).then(res => {
        if (res.code === 200) {
          this.drugDelList = res.rows;
          this.total = res.total;
        }
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      console.log(this.drugIds)
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导入按钮操作 */
    handleImportTable() {
      const drugsIds = this.drugIds.join(",");
      console.log(drugsIds)
      if (drugsIds == "") {
        this.$modal.msgError("请选择要导入的药品");
        return;
      }
      importDrug({drugIds:drugsIds}).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$parent.getList();
        }
      });
    }
  }
};
</script>
