<?php

class AdminAction extends LoginSessionAction{

    /**
     * 首页
     */
    public function index(){
        //创建时间
        if(isset($_POST["start_time"]) && isset($_POST["end_time"])){
            if(I('post.start_time','','htmlspecialchars')!=""  &&I('post.end_time','','htmlspecialchars')!=""){
                $where_admin["admin_createtime"] =array("between",array(I('post.start_time','','htmlspecialchars')." 00:00:00",I('post.end_time','','htmlspecialchars')." 23:59:59"));
            }elseif(I('post.start_time','','htmlspecialchars')!=""){
                $where_admin["admin_createtime"] =array("egt",I('post.start_time','','htmlspecialchars')." 00:00:00");
            }elseif(I('post.end_time','','htmlspecialchars')!=""){
                $where_admin["admin_createtime"] =array("elt",I('post.end_time','','htmlspecialchars')." 23:59:59");
            }
            $this->assign("start_time",$_POST["start_time"]);
            $this->assign("end_time",$_POST["end_time"]);
        }

        //在职状态
        if(isset($_POST["admin_work"])){
            if(I("post.admin_work")){
                $where_admin["admin_work"] =I("post.admin_work");
            }
            $this->assign("admin_work",I("post.admin_work"));
        }

        //登录状态
        if(isset($_POST["admin_login_state"])){
            if(I("post.admin_login_state")){
                $where_admin["admin_login_state"] =I("post.admin_login_state");
            }
            $this->assign("admin_login_state",I("post.admin_login_state"));
        }

        //登录账号
        if(isset($_POST["admin_account"])){
            if(I("post.admin_account")!=""){
                $where_admin["admin_account"] =array("like","%".I("post.admin_account")."%");
            }
            $this->assign("admin_account",I("post.admin_account"));
        }
        //姓名
        if(isset($_POST["admin_name"])){
            if(I("post.admin_name")!=""){
                $where_admin["admin_name"] =array("like","%".I("post.admin_name")."%");
            }
            $this->assign("admin_name",I("post.admin_name"));
        }

        $where_admin["admin_delmark"] =0;
        $admin = M()
            ->table("tp_admin u")
            ->join("left join tp_department d on d.department_id = u.admin_department_id")
            ->field("u.admin_id,u.admin_createtime,u.admin_account,admin_ip,u.admin_name,u.admin_login_time,u.admin_login_ip,u.admin_login_state,u.admin_login_state_time,u.admin_work,u.admin_work_time,d.department_name")
            ->where($where_admin)->order("admin_order asc,admin_id desc")->select();
        $this->assign("admin",$admin);

        //echo M()->getLastSql();

        $this->display();
    }


    /**
     * 回收站
     */
    public function delmarklist(){

        //注册时间
        if(isset($_POST["start_time"]) && isset($_POST["end_time"])){
            if(I('post.start_time','','htmlspecialchars')!=""  &&I('post.end_time','','htmlspecialchars')!=""){
                $where_admin["admin_createtime"] =array("between",array(I('post.start_time','','htmlspecialchars')." 00:00:00",I('post.end_time','','htmlspecialchars')." 23:59:59"));
            }elseif(I('post.start_time','','htmlspecialchars')!=""){
                $where_admin["admin_createtime"] =array("egt",I('post.start_time','','htmlspecialchars')." 00:00:00");
            }elseif(I('post.end_time','','htmlspecialchars')!=""){
                $where_admin["admin_createtime"] =array("elt",I('post.end_time','','htmlspecialchars')." 23:59:59");
            }
            $this->assign("start_time",$_POST["start_time"]);
            $this->assign("end_time",$_POST["end_time"]);
        }

        //在职状态
        if(isset($_POST["admin_work"])){
            if(I("post.admin_work")){
                $where_admin["admin_work"] =I("post.admin_work");
            }
            $this->assign("admin_work",I("post.admin_work"));
        }

        //登录状态
        if(isset($_POST["admin_login_state"])){
            if(I(post."admin_login_state")){
                $where_admin["admin_login_state"] =I("post.admin_login_state");
            }
            $this->assign("admin_login_state",I("post.admin_login_state"));
        }

        //登录账号
        if(isset($_POST["admin_account"])){
            if(I("post.admin_account")!=""){
                $where_admin["admin_account"] =array("like","%".I("post.admin_account")."%");
            }
            $this->assign("admin_account",I("post.admin_account"));
        }
        //姓名
        if(isset($_POST["admin_name"])){
            if(I("post.admin_name")!=""){
                $where_admin["admin_name"] =array("like","%".I("post.admin_name")."%");
            }
            $this->assign("admin_name",I("post.admin_name"));
        }

        $where_admin["admin_delmark"] =1;
        $admin = M()
            ->table("tp_admin u")
            ->join("left join tp_department d on d.department_id = u.admin_department_id")
            ->field("u.admin_id,u.admin_createtime,u.admin_account,u.admin_name,u.admin_login_time,u.admin_login_ip,u.admin_login_state,u.admin_login_state_time,u.admin_work,u.admin_work_time,d.department_name")
            ->where($where_admin)->select();
        $this->assign("admin",$admin);

        $this->display();
    }

    //删除
    public function delmark(){
        $admin_session=session("admin_session");

        $where_admin_update["admin_id"] = I("post.admin_id");
        $data_admin_update["admin_delmark"] = I("post.admin_delmark",0);
        $r = M("admin")->where($where_admin_update)->data($data_admin_update)->save();

        if($r!==false){

            $where_shop["shop_adminid"] = I("post.admin_id");
            $data_shop["shop_delmark"] = I("post.admin_delmark",0);
            $r = M("shop")->where($where_shop)->data($data_shop)->save();

            $attr["code"] = 200;
        }else{
            $attr["code"] = 0;
            $attr["info"] = "操作失败";
        }
        $this->ajaxReturn($attr);
    }

    //禁用|启用
    public function login_state(){
        $admin_session=session("admin_session");

        $where_admin_update["admin_id"] = I("post.admin_id");
        $data_admin_update["admin_login_state"] = I("post.admin_login_state");
        $data_admin_update["admin_login_state_time"]= date('Y-m-d H:i:s',time());
        $data_admin_update["admin_login_state_admin_id"] = $admin_session["admin_id"];
        $r = M("admin")->where($where_admin_update)->data($data_admin_update)->save();

        if($r!==false){
            $attr["code"] = 200;
            $attr["admin_login_state_time"] = $data_admin_update["admin_login_state_time"];
        }else{
            $attr["code"] = 0;
            $attr["info"] = "操作失败";
        }
        $this->ajaxReturn($attr);
    }

    //在职状态
    public function admin_work(){
        $admin_session=session("admin_session");

        $where_admin_update["admin_id"] = I("post.admin_id");
        $data_admin_update["admin_work"] = I("post.admin_work");
        $data_admin_update["admin_work_time"]= date('Y-m-d H:i:s',time());
        $data_admin_update["admin_work_admin_id"] = $admin_session["admin_id"];
        $r = M("admin")->where($where_admin_update)->data($data_admin_update)->save();

        if($r!==false){
            $attr["code"] = 200;
            $attr["admin_work_time"] = $data_admin_update["admin_work_time"];
        }else{
            $attr["code"] = 0;
            $attr["info"] = "操作失败";
        }
        $this->ajaxReturn($attr);
    }

    //修改密码
    public function admin_psd(){
        $admin_session=session("admin_session");
        if($this->isPost()){
            $where_admin["admin_id"] = $admin_session["admin_id"];
            $where_admin["admin_psd"] = I("post.admin_psd");
            $admin = M("admin")->where($where_admin)->find();
            if(count($admin)!=0){
                $where_admin_update["admin_id"] = $admin_session["admin_id"];
                $where_admin_update["admin_psd"] = I("post.admin_psd");
                $data_admin_update["admin_psd"] = I("post.admin_psd_new");
                $r = M("admin")->where($where_admin_update)->data($data_admin_update)->save();
                if($r!==false){
                    $attr["code"] = 200;
                }else{
                    $attr["code"] = 0;
                    $attr["info"] = "修改失败";
                }
            }else{
                $attr["code"] = 0;
                $attr["info"] = "原始密码错误";
            }
            $this->ajaxReturn($attr);
        }
        $this->display();
    }

    //维护
    public function manage(){
        //查询部门
        $where_department["department_father"] = 0;
        $where_department["department_delmark"] = 0;
        $department = M("department")->field("department_id,department_name")->where($where_department)->select();
        $this->assign("department",$department);

        //获取小区
        $where_village["village_delmark"] = 0;
        $village = M("village")->where($where_village)->select();
        $this->assign('village', $village);

        if(isset($_GET["admin_id"])){
            $where_admin["admin_id"] = I("admin_id");
            $admin = M()
                ->table("tp_admin a")
                ->join("left join tp_department d on d.department_id = a.admin_department_id")
                ->field("a.*,d.department_id,d.department_father")
                ->where($where_admin)->find();

            $this->assign("admin",$admin);

            if($admin["department_father"]!=0){
                $where_department["department_father"] = $admin["department_father"];
                $where_department["department_delmark"] = 0;
                $departments = M("department")->field("department_id,department_name")->where($where_department)->select();
                $this->assign("departments",$departments);

                $this->assign("department_id1",$admin["department_father"]);
                $this->assign("department_id2",$admin["department_id"]);
            }else{
                $where_department["department_father"] = $admin["department_id"];
                $where_department["department_delmark"] = 0;
                $departments = M("department")->field("department_id,department_name")->where($where_department)->select();
                $this->assign("departments",$departments);

                $this->assign("department_id1",$admin["department_id"]);
                $this->assign("department_id2",0);
            }

            $where['shop_adminid'] = $admin['admin_id'];
            $shop_info = M('shop')->where($where)->find();
            if(!$shop_info)
            {
                $data_shop['shop_adminid'] = $admin['admin_id'];
                $shop_info_id = M('shop')->data($data_shop)->add();
            }else{
                $shop_info_id = $shop_info["shop_id"];
            }
            //查询
            $where_shop_village["shop_village_shopid"] = $shop_info_id;
            $shop_village = M("shop_village")->where($where_shop_village)->select();

            $str = "";
            for ($i = 0; $i < count($shop_village); $i++) {
                if ($str == "") {
                    $str = $shop_village[$i]["shop_village_villageid"];
                } else {
                    $str = $str . "," . $shop_village[$i]["shop_village_villageid"];
                }
            }
            $this->assign('villageid', $str);

        }

        $this->display();
    }

    //修改密码
    public function saves(){

        $data_admin["admin_name"] = I("post.admin_name");
        $data_admin["admin_account"] = I("post.admin_account");
        $data_admin["admin_department_id"] = I("post.admin_department_id");

        if(I("post.admin_psd") && I("post.admin_psd")!=""){
            $data_admin["admin_psd"] = I("post.admin_psd");
        }

        if(I("post.admin_ip") && I("post.admin_ip")!=""){
            $data_admin["admin_ip"] = I("post.admin_ip");
        }

        if(I("post.admin_ip_address") && I("post.admin_ip_address")!=""){
            $data_admin["admin_ip_address"] = I("post.admin_ip_address");
        }

        $data_admin["admin_export"] = I("post.admin_export")==1?1:0;
        $data_admin["admin_order"] = I("post.admin_order")?:0;
        $data_admin["admin_cuishou"] = I("post.admin_cuishou")==1?1:0;
        $data_admin["admin_xujie"] = I("post.admin_xujie")==1?1:0;
        $data_admin["admin_delmark_clear"] = I("post.admin_delmark_clear")==1?1:0;

        if(I("post.admin_id")){
            $where_admins["admin_id"] = I("post.admin_id");
            $admin = M("admin")->where($where_admins)->find();
            if($admin["admin_account"] != I("post.admin_account")){
                $where_admin_find["admin_account"] = I("post.admin_account");
                $admin = M("admin")->field("admin_id,admin_delmark")->where($where_admin_find)->find();
                if(count($admin)!=0){
                    $attr["code"] = 0;
                    if($admin["admin_delmark"]==1){
                        $attr["info"] = "已存在回收站中";
                    }else{
                        $attr["info"] = "已存在";
                    }
                    $this->ajaxReturn($attr);
                }
            }
            $where_admin["admin_id"] = I("post.admin_id");
            $dp_admin_id = I("post.admin_id");
            $r = M("admin")->where($where_admin)->data($data_admin)->save();
        }else{
            $where_admin_find["admin_account"] = I("post.admin_account");
            $admin = M("admin")->where($where_admin_find)->find();
            if(count($admin)!=0){
                $attr["code"] = 0;
                $attr["info"] = "账号已存在";
                $this->ajaxReturn($attr);
            }
            $r = M("admin")->add($data_admin);
            $dp_admin_id = $r;
        }

        if($r!==false){

            //保存小区
            $where_shop['shop_adminid'] = $dp_admin_id;
            $shop_info = M('shop')->where($where_shop)->find();


            //添加小区
            $shop_village_villageid = $_POST["shop_village_villageid"];


            if ($shop_info) {
                $shop_id = $shop_info["shop_id"];
            } else {
                $shop_data['shop_adminid'] = $dp_admin_id;
                $shop_id = M('shop')->data($shop_data)->add();
            }
            if ($shop_id) {
                M("shop_village")->where("shop_village_shopid =" . $shop_id)->delete();
                $str = explode(",", $shop_village_villageid);
                $shop_village = array();
                for ($i = 0; $i < count($str); $i++) {
                    $shop_villages = array();
                    $shop_villages["shop_village_villageid"] = $str[$i];
                    $shop_villages["shop_village_shopid"] = $shop_id;

                    $shop_village[$i] = $shop_villages;
                }
                M("shop_village")->addAll($shop_village);
            }
            $attr["code"] = 200;
        }else{
            $attr["code"] = 0;
            $attr["info"] = "操作失败";
        }
        $this->ajaxReturn($attr);

    }

    //部门选择
    public function department_admin(){
        $where_admin["admin_department_id"] = I("post.department_id");
        $where_admin["admin_delmark"] = 0;
        $admin = M("admin")->field("admin_id,admin_name")->where($where_admin)->select();
        $htmls = '<option value="0">审核人员</option>';
        for($i=0;$i<count($admin);$i++){
            $htmls = $htmls .'<option value="'.$admin[$i]["admin_id"].'">'.$admin[$i]["admin_name"].'</option>';
        }
        echo $htmls;
    }
}

function foo($a){

}