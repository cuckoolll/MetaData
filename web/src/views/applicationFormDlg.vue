<template>
  <el-dialog v-model="showApplicationFormDlg"
             :title="title"
             width="1300px"
             top="6vh"
             :close-on-click-modal="false"
  >
    <el-form
        :model="applicationForm"
        ref="applicationForm"
        :rules="rules">
      <el-row>
        <el-col :span="8">
          <el-form-item label="数据库名称" :label-width="formLabelWidth" prop="schema">
            <el-select v-model="applicationForm.tableSchema"
                       clearable
                       style="width: 250px;"
                       :disabled="optType !== 'create_table'">
              <el-option
                  v-for="item in schemaOption"
                  :key="item.id"
                  :label="item.text"
                  :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="表名" :label-width="formLabelWidth" prop="tableName">
            <el-input v-model="applicationForm.tableName"
                      type="text"
                      style="width: 250px;"
                      :disabled="optType !== 'create_table'"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="表备注" :label-width="formLabelWidth" prop="remark">
            <el-input v-model="applicationForm.remark"
                      type="text"
                      style="width: 250px;"
                      :disabled="optType === 'del_table' || optType === 'show_table'"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="说明" :label-width="formLabelWidth" prop="description">
            <el-input v-model="applicationForm.description"
                      type="textarea"
                      rows="1"
                      style="width: 94%"
                      :disabled="optType === 'show_table'"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div>
      <el-button v-if="optType !== 'del_table' && optType !== 'show_table'"
                 type="primary"
                 icon="plus"
                 text
                 @click="addItem">创建</el-button>
    </div>
    <div style="padding-top: 5px;">
      <el-tabs v-model="tabsActiveName" class="demo-tabs" type="border-card" @tab-change="tabChange">
        <el-tab-pane label="字段" name="first">
          <el-table
              :data="columnsData"
              height="35vh"
              border
              style="width: 100%; ">
            <el-table-column
                type="index"
                label="序号"
                align="center"
                :width="80">
            </el-table-column>
            <el-table-column
                v-if="optType !== 'del_table'"
                prop="optTypeStr"
                label="操作类型"
                align="center"
                :width="100">
            </el-table-column>
            <el-table-column
                prop="columnName"
                label="字段名称"
                align="center"
                :width="180">
              <template #default="scope">
                <el-input v-if="scope.row.columnNameErr"
                          v-model="scope.row.columnName"
                          type="text"
                          class="errorInput"
                          :disabled = "scope.row.optType !== 'add_column'"></el-input>
                <el-input v-else
                          v-model="scope.row.columnName"
                          type="text"
                          :disabled = "scope.row.optType !== 'add_column' || optType === 'show_table'"></el-input>
              </template>
            </el-table-column>
            <el-table-column
                prop="dataType"
                label="数据类型"
                align="center"
                :width="140">
              <template #default="scope">
                <el-select v-if="scope.row.dataTypeErr"
                           v-model="scope.row.dataType"
                           class="errorInput"
                           :disabled="scope.row.isEditAble === false">
                  <el-option
                      v-for="item in dataTypeSelect"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                  >
                  </el-option>
                </el-select>
                <el-select v-else
                           v-model="scope.row.dataType"
                           :disabled="scope.row.isEditAble === false || optType === 'show_table'">
                  <el-option
                      v-for="item in dataTypeSelect"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
                prop="columnDefault"
                label="默认值"
                align="center"
                :width="110">
              <template #default="scope">
                <el-input v-model="scope.row.columnDefault"
                          type="text"
                          :disabled="scope.row.isEditAble === false || optType === 'show_table'"></el-input>
              </template>
            </el-table-column>
            <el-table-column
                prop="isPrimary"
                label="是否主键"
                align="center"
                :width="90">
              <template #default="scope">
                <el-select v-model="scope.row.isPrimary"
                           clearable
                           placeholder=" "
                           :disabled="scope.row.isPrimaryAble==false || optType!=='create_table'">
                  <el-option
                      v-for="item in yesOrNoSelect"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
                prop="isNullable"
                label="是否为空"
                align="center"
                :width="90">
              <template #default="scope">
                <el-select v-model="scope.row.isNullable"
                           placeholder=" "
                           :disabled="optType!=='create_table'">
                  <el-option
                      v-for="item in yesOrNoSelect"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
                prop="columnSize"
                label="字段长度"
                align="center"
                :width="100">
              <template #default="scope">
                <el-input v-if="scope.row.columnSizeErr"
                          v-model="scope.row.columnSize"
                          type="number"
                          :disabled="scope.row.isSizeAble === false || scope.row.isEditAble === false"
                          class="errorInput">
                </el-input>
                <el-input v-else
                          v-model="scope.row.columnSize"
                          type="number"
                          :disabled="scope.row.isSizeAble === false || scope.row.isEditAble === false || optType === 'show_table'">
                </el-input>
              </template>
            </el-table-column>
            <el-table-column
                prop="numberScale"
                label="字段精度"
                align="center"
                :width="100">
              <template #default="scope">
                <el-input v-model="scope.row.numberScale"
                          type="number"
                          :disabled="scope.row.isScaleAble === false || scope.row.isEditAble === false || optType === 'show_table'"></el-input>
              </template>
            </el-table-column>
            <el-table-column
                prop="extra"
                label="扩展方法"
                align="center"
                :width="145">
              <template #default="scope">
                <el-input v-model="scope.row.extra" type="text" :disabled="optType !== 'create_table'"></el-input>
              </template>
            </el-table-column>
            <el-table-column
                prop="remark"
                label="备注"
                align="center"
                :width="220">
              <template #default="scope">
                <el-input v-if="scope.row.remarkErr"
                          v-model="scope.row.remark"
                          type="text"
                          class="errorInput"
                          :disabled="scope.row.isEditAble === false"></el-input>
                <el-input v-else
                          v-model="scope.row.remark"
                          type="text"
                          :disabled="scope.row.isEditAble === false || optType === 'show_table'"></el-input>
              </template>
            </el-table-column>
            <el-table-column
                v-if="optType !== 'del_table' && optType !== 'show_table'"
                label="操作"
                fixed="right"
                align="center"
                :width="105">
              <template #default="scope">
                <el-button type="primary"
                           icon="Edit"
                           circle
                           v-if="optType !== 'create_table'"
                           @click="columnEdit(scope.$index)"></el-button>
                <el-button type="danger" icon="Delete" circle @click="deleteRow(scope.$index)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="索引" name="second">
          <el-table
              :data="indexData"
              height="35vh"
              border
              style="width: 100%; ">
            <el-table-column
                type="index"
                label="序号"
                align="center"
                :width="80">
            </el-table-column>
            <el-table-column
                v-if="optType !== 'del_table'"
                prop="optTypeStr"
                label="操作类型"
                align="center"
                :width="100">
            </el-table-column>
            <el-table-column
                prop="indexName"
                label="索引名称"
                align="center">
              <template #default="scope">
                <el-input v-if="scope.row.indexNameErr"
                          v-model="scope.row.indexName"
                          type="text"
                          class="errorInput"
                          :disabled="scope.row.optType != 'add_index'"></el-input>
                <el-input v-else
                          v-model="scope.row.indexName"
                          type="text"
                          :disabled="scope.row.optType != 'add_index' || optType === 'show_table'"></el-input>
              </template>
            </el-table-column>
            <el-table-column
                prop="columnNameList"
                label="字段名称"
                align="center">
              <template #default="scope">
                <el-select v-if="scope.row.columnNameListErr"
                           v-model="scope.row.columnNameList"
                           multiple
                           class="errorInput"
                           :disabled="scope.row.optType != 'add_index'">
                  <el-option
                      v-for="item in columnSelect"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                  >
                  </el-option>
                </el-select>
                <el-select v-else
                           v-model="scope.row.columnNameList"
                           multiple
                           :disabled="scope.row.optType != 'add_index' || optType === 'show_table'">
                  <el-option
                      v-for="item in columnSelect"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
                prop="isUnique"
                label="是否唯一索引"
                align="center">
              <template #default="scope">
                <el-select v-if="scope.row.isUniqueErr"
                           v-model="scope.row.isUnique"
                           class="errorInput"
                           :disabled="scope.row.optType != 'add_index'">
                  <el-option
                      v-for="item in yesOrNoSelect"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                  >
                  </el-option>
                </el-select>
                <el-select v-else
                           v-model="scope.row.isUnique"
                           :disabled="scope.row.optType != 'add_index' || optType === 'show_table'">
                  <el-option
                      v-for="item in yesOrNoSelect"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
                v-if="optType !== 'del_table' && optType !== 'show_table'"
                label="操作"
                fixed="right"
                align="center"
                :width="105">
              <template #default="scope">
                <el-button type="danger" icon="Delete" circle @click="deleteRow(scope.$index)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>

    <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeDlg">取消</el-button>
          <el-button
              type="primary"
              @click="save('applicationForm')"
              v-if="optType !== 'show_table'"
          >保存</el-button>
          <el-button
              type="primary"
              @click="exportApplicationForm()"
              v-if="optType === 'show_table'"
          >导出</el-button>
        </span>
    </template>
  </el-dialog>
</template>

<script>
import dbManager from "@/api/dbManager";
import dbConf from "@/api/dbConf";
import dbOption from "@/api/dbOption";
import {doExport} from "@/utils/export";

export default {
  name: "applicationFormDlg",
  data() {
    return {
      formLabelWidth: '100px',
      tabsActiveName: 'first',
      showApplicationFormDlg : false,
      title : '',
      optType: '',
      optId: '',

      applicationForm: {},
      rules: {
        tableSchema: [{ required: true, message: '请选择数据库名称', trigger: 'blur' }],
        tableName: [{ required: true, message: '请输入表名', trigger: 'blur' }],
        // remark: [{ required: true, message: '请输入表备注', trigger: 'blur' }],
        description: [{ required: true, message: '请输入说明', trigger: 'blur' }],
      },

      schemaOption: [],
      dataTypeSelect: [],
      yesOrNoSelect: [
        {id:'0', text:"否"},
        {id:'1', text:"是"},
      ],
      columnSelect: [],

      columnsData: [],
      indexData: [],

      columnOptTypeIndex: [],
      indexOptTypeIndex: [],
    }
  },

  methods: {
    showApplicationForm(form) {
      this.title = form.title;
      this.optType = form.optType;
      this.optId = form.optId;
      this.columnsData = [];
      this.indexData = [];
      this.applicationForm = {};
      this.applicationForm = form;
      if (this.optId) {
        this.getOptionById(form.optId);
      } else if ('create_table' != this.optType) {
        this.getDbColumn(form.tableName, form.tableSchema);
        this.getDbIndex(form.tableName, form.tableSchema);
      }
      this.showApplicationFormDlg = true;
    },

    closeDlg() {
      this.showTableDetailDlg = false;
      this.$emit("on-close");
    },

    tabChange(tabName) {
      this.columnSelect = [];
      for (let i = 0; i < this.columnsData.length; i++) {
        if (this.columnsData[i].columnName) {
          let obj = {};
          obj.id = this.columnsData[i].columnName;
          obj.text = this.columnsData[i].columnName;
          this.columnSelect.push(obj);
        }
      }
    },

    addItem() {
      if (this.tabsActiveName == 'first') { //字段
        this.columnsData.push({
          optType:'add_column',
          optTypeStr:this.columnOptTypeIndex['add_column'],
          isEditAble:true
        });
      } else if (this.tabsActiveName == 'second') { //索引
        this.indexData.push({
          optType:'add_index',
          optTypeStr:this.indexOptTypeIndex['add_index'],
          isEditAble:true
        });
      }
    },

    deleteRow(index) {
      if (this.tabsActiveName == 'first') { //字段
        if ('create_table' != this.optType && this.columnsData[index].optType != 'add_column') {
          this.columnsData[index].isEditAble = false;
          this.columnsData[index].optType = 'del_column';
          this.columnsData[index].optTypeStr = this.columnOptTypeIndex['del_column'];
        } else {
          this.columnsData.splice(index, 1);
        }
      } else if (this.tabsActiveName == 'second') { //索引
        if ('create_table' != this.optType && this.indexData[index].optType != 'add_index') {
          this.indexData[index].optType = 'del_index';
          this.indexData[index].optTypeStr = this.indexOptTypeIndex['del_index'];
        } else {
          this.indexData.splice(index, 1);
        }
      }
    },

    columnEdit(index) {
      this.columnsData[index].isEditAble = true;
      this.columnsData[index].optType = 'edit_column';
      this.columnsData[index].optTypeStr = this.columnOptTypeIndex['edit_column'];
    },

    checkColumnAndIndexValid() {
      let errCount = 0;
      if (this.columnsData.length == 0) {
        errCount++;
        this.$message.warning("创建表时，至少包含一个字段");
        return errCount;
      }

      for (let i = 0; i < this.columnsData.length; i++) {
        let col = this.columnsData[i];
        this.columnsData[i].columnNameErr = false;
        this.columnsData[i].dataTypeErr = false;
        this.columnsData[i].columnSizeErr = false;
        this.columnsData[i].remarkErr = false;
        if (!col.columnName || col.columnName.trim() == '') {
          this.columnsData[i].columnNameErr = true;
          errCount++;
        }
        if (!col.dataType || col.dataType.trim() == '') {
          this.columnsData[i].dataTypeErr = true;
          errCount++;
        }
        if (!col.columnSize || col.columnSize == '') {
          if (col.dataType != 'timestamp' && col.dataType != 'datetime') {
            this.columnsData[i].columnSizeErr = true;
            errCount++;
          }
        }
        if (!col.remark || col.remark.trim() == '') {
          this.columnsData[i].remarkErr = true;
          errCount++;
        }
      }

      for (let i = 0; i < this.indexData.length; i++) {
        let index = this.indexData[i];
        this.indexData[i].indexNameErr = false;
        this.indexData[i].columnNameListErr = false;
        this.indexData[i].isUniqueErr = false;
        if (!index.indexName || index.indexName.trim() == '') {
          this.indexData[i].indexNameErr = true;
          errCount++;
        }
        if (!index.columnNameList || index.columnNameList.length == 0) {
          this.indexData[i].columnNameListErr = true;
          errCount++;
        }
        if (!index.isUnique || index.isUnique == '') {
          this.indexData[i].isUniqueErr = true;
          errCount++;
        }
      }

      if (errCount > 0) {
        this.$message.warning("字段或索引存在不规范数据，请检查");
      }
      return errCount;
    },

    async save(applicationForm) {
      let that = this;
      this.$refs[applicationForm].validate(async (valid) => {
        if (valid) {
          if (that.optType !== 'del_table') {
            let errCount = this.checkColumnAndIndexValid();
            if (errCount > 0) {
              return false;
            }
          }

          let option = this.applicationForm;
          option.columnList = this.columnsData;
          option.indexList = this.indexData;
          const { code, data, msg } = await dbOption.createOption(option);
          if ('200' == code) {
            let optId = data;
            let res = await dbOption.finishOption(optId);
            if ('200' == res.code) {
              this.$message.success("保存成功，单号：" + optId);
              this.closeDlg();
            } else {
              this.$message.error(res.msg);
            }
          } else {
            this.$message.error(msg);
          }
        } else {
          this.checkColumnAndIndexValid();
          return false;
        }
      });

      if (this.columnsData.length > 0) {
        this.columnsData[0].isColumnNameErr = true;
      }
    },

    async exportApplicationForm() {
      const { code, data, msg } = await dbOption.exportApplicationForm(this.optId);
      if ('200' != code) {
        this.$message.error(msg);
        return;
      }
      doExport(data, "变更记录单_" + this.optId + `.sql`);
    },

    async getDbColumn(tableName, schema) {
      const { code, data, msg } = await dbManager.getDbColumn({'tableName':tableName, 'schema':schema});
      if ('200' == code) {
        if (data && data.length > 0) {
          for (let i = 0; i < data.length; i++) {
            data[i].isEditAble = false;
            data[i].isPrimary = data[i].isPrimary ? data[i].isPrimary + '' : '';
            data[i].isNullable = data[i].isNullable ? data[i].isNullable + '' : '';
            data[i].columnSize = data[i].varcharLength ? data[i].varcharLength : data[i].numberLength;
          }
        }
        this.columnsData = data;
      }
    },

    async getDbIndex(tableName, schema) {
      const { code, data, msg } = await dbManager.getDbIndex({'tableName':tableName, 'schema':schema});
      if ('200' == code) {
        if (data) {
          for (let i = 0; i < data.length; i++) {
            let columnNameList = [];
            let columnName = data[i].columnName;
            if (columnName.indexOf(",") > 0) {
              columnNameList = columnName.split(",")
            } else {
              columnNameList.push(columnName);
            }
            data[i].columnNameList = columnNameList;
            data[i].isUnique = data[i].isUnique ? data[i].isUnique + '' : '0';
          }
        }
        this.indexData = data;
      }
    },

    async getSchemaSelect() {
      const { code, data, msg } = await dbConf.getSchemaSelect();
      if ('200' == code) {
        this.schemaOption = data;
      }
    },

    async getDataTypeSelect() {
      const { code, data, msg } = await dbConf.getDataTypeSelect();
      if ('200' == code) {
        this.dataTypeSelect = data;
      }
    },

    async getOptionById(optId) {
      const { code, data, msg } = await dbOption.getOptionById(optId);
      if ('200' == code) {
        if (data) {
          this.applicationForm = data;
          this.columnsData = data.columnList;
          this.indexData = data.indexList;

          for (let i = 0; i < this.columnsData.length; i++) {
            this.columnsData[i].columnSize = this.columnsData[i].varcharLength ? this.columnsData[i].varcharLength : this.columnsData[i].numberLength;
            this.columnsData[i].optTypeStr = this.columnOptTypeIndex[this.columnsData[i].optType];
            this.columnsData[i].isPrimary = this.columnsData[i].isPrimary ? this.columnsData[i].isPrimary + '' : '';
            this.columnsData[i].isNullable = this.columnsData[i].isNullable ? this.columnsData[i].isNullable + '' : '0';
          }
          for (let i = 0; i < this.indexData.length; i++) {
            this.indexData[i].optTypeStr = this.indexOptTypeIndex[this.indexData[i].optType];
            this.indexData[i].isUnique = this.indexData[i].isUnique ? this.indexData[i].isUnique + '' : '0';
          }
        }
      }
    },

    async getColumnOptTypeSelect() {
      const { code, data, msg } = await dbOption.getColumnOptTypeSelect();
      if ('200' == code) {
        for (let i = 0; i < data.length; i++) {
          this.columnOptTypeIndex[data[i].id] = data[i].text;
        }
      }
    },

    async getIndexOptTypeSelect() {
      const { code, data, msg } = await dbOption.getIndexOptTypeSelect();
      if ('200' == code) {
        for (let i = 0; i < data.length; i++) {
          this.indexOptTypeIndex[data[i].id] = data[i].text;
        }
      }
    },
  },

  mounted() {
    this.getDataTypeSelect();
    this.getColumnOptTypeSelect();
    this.getIndexOptTypeSelect();
    this.getSchemaSelect();
  },

  watch: {
    'columnsData' : {
      handler (newArr, oldArr) {
        let hasPrimaryKey = false;
        for (let i = 0; i < this.columnsData.length; i++) {
          if (this.columnsData[i].dataType == 'decimal') {
            this.columnsData[i].isScaleAble = true;
            this.columnsData[i].isSizeAble = true;
          } else {
            this.columnsData[i].isScaleAble = false;
            switch (this.columnsData[i].dataType) {
              case 'tinyint': {
                this.columnsData[i].isSizeAble = false;
                this.columnsData[i].columnSize = 3;
                break;
              };
              case 'int': {
                this.columnsData[i].isSizeAble = false;
                this.columnsData[i].columnSize = 10;
                break;
              };
              case 'bigint': {
                this.columnsData[i].isSizeAble = false;
                this.columnsData[i].columnSize = 19;
                break;
              };
              case 'text': {
                this.columnsData[i].isSizeAble = false;
                this.columnsData[i].columnSize = 65535;
                break;
              };
              case 'longtext': {
                this.columnsData[i].isSizeAble = false;
                this.columnsData[i].columnSize = 4294967295;
                break;
              };
              case 'timestamp': {
                this.columnsData[i].isSizeAble = false;
                this.columnsData[i].columnSize = null;
                break;
              };
              case 'datetime': {
                this.columnsData[i].isSizeAble = false;
                this.columnsData[i].columnSize = null;
                break;
              };
              default : {
                this.columnsData[i].isSizeAble = true;
              }
            }
          }

          if (this.columnsData[i].isPrimary === '1') {
            hasPrimaryKey = true;
            this.columnsData[i].isNullable = '0';
          } else {
            this.columnsData[i].isNullable = null;
          }
        }

        for (let i = 0; i < this.columnsData.length; i++) {
          if (hasPrimaryKey && this.columnsData[i].isPrimary !== '1') {
            this.columnsData[i].isPrimaryAble = false;
          }
          if (!hasPrimaryKey) {
            this.columnsData[i].isPrimaryAble = true;
          }
        }
      },
      deep: true,
    },
  },
}
</script>

<style scoped>
.inputWidth{
  width: 200px;
}

.errorInput {
  border:1px solid red;
  border-radius: 4px;
}
</style>