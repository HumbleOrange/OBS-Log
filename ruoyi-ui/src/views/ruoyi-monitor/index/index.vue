<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="跟踪号" prop="trackId">
        <el-input
          v-model="queryParams.trackId"
          placeholder="请输入跟踪号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时间">
        <el-date-picker
          v-model="daterangeTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="日志等级" prop="level">
        <el-input
          v-model="queryParams.level"
          placeholder="请输入日志等级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="日志类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择日志类型" clearable>
          <el-option
            v-for="dict in dict.type.log_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务键" prop="businessId">
        <el-input
          v-model="queryParams.businessId"
          placeholder="请输入业务键"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="indexList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="跟踪号" align="center" prop="trackId" />
      <el-table-column label="时间" align="center" prop="time" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="日志等级" align="center" prop="level" />
      <el-table-column label="日志类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.log_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="业务键" align="center" prop="businessId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 详情对话框 -->
    <el-dialog :title="'日志详情'" :visible.sync="dialogVisible" width="800px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="ID">{{ logDetail.id }}</el-descriptions-item>
        <el-descriptions-item label="跟踪号">{{ logDetail.trackId }}</el-descriptions-item>
        <el-descriptions-item label="时间">{{ parseTime(logDetail.time) }}</el-descriptions-item>
        <el-descriptions-item label="日志等级">{{ logDetail.level }}</el-descriptions-item>
        <el-descriptions-item label="分组">{{ logDetail.group }}</el-descriptions-item>
        <el-descriptions-item label="类型">{{ logDetail.type }}</el-descriptions-item>
        <el-descriptions-item label="地址">{{ logDetail.address }}</el-descriptions-item>
        <el-descriptions-item label="业务键">{{ logDetail.businessId }}</el-descriptions-item>
        <el-descriptions-item label="所有者">{{ logDetail.owner }}</el-descriptions-item>
        <el-descriptions-item label="消息" :span="2">{{ logDetail.message }}</el-descriptions-item>
        <el-descriptions-item label="上下文" :span="2">{{ logDetail.context }}</el-descriptions-item>
        <el-descriptions-item label="异常信息" :span="2">{{ logDetail.exception }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

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
import { listIndex, getLog } from "@/api/ruoyi-monitor/index";

export default {
  name: "Index",
  dicts: ['log_type'],
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
      // 日志索引表格数据
      indexList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 业务键时间范围
      daterangeTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        trackId: null,
        time: null,
        level: null,
        type: null,
        businessId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 详情对话框显示状态
      dialogVisible: false,
      // 日志详情数据
      logDetail: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询日志索引列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeTime && '' != this.daterangeTime) {
        this.queryParams.params["beginTime"] = this.daterangeTime[0];
        this.queryParams.params["endTime"] = this.daterangeTime[1];
      }
      listIndex(this.queryParams).then(response => {
        this.indexList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.resetForm("queryForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
    },
    // 查看详情按钮操作
    handleDetail(row) {
      this.dialogVisible = true;
      getLog(row.trackId, row.id).then(response => {
        this.logDetail = response;
      });
    }
  }
};
</script>
