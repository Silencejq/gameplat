<?php

namespace app\jiadian\controller;

use think\Controller;
use think\Request;
use app\jiadian\model\User;
use think\facade\Cookie;

class Login extends Controller
{
    public function login(Request $request){
        $username = $request->param('userName');
        $password = md5($request->param('userPassWord'));
        $user= User::get(['username'=>"$username"]);
        $role = $user->userrole;
        $department = $user->department;
        switch($role){
            case '管理员':$number=1;break; 
            case '销售':$number=2;break;
            case '售后':$number=3;break;
            case '经理':$number=4;break;
            default:$number=5;
        }

        if(($user->userpwd)===$password){
            Cookie::delete('role');
            Cookie::delete('department');
            Cookie::set('role',$number);
            Cookie::set('department',$department);
            $data=array("id"=>"$user->userid","name"=>"$user->username",'role'=>$number);
            $q=array("message"=>"Success","data"=>$data);
        }
        else{
            $data=array("id"=>"aaa","name"=>"bbb",'role'=>'管理员');
            $q=array("message"=>"wrong","data"=>$data);
        }
        $p=json_encode($q);
        echo $p;
    }
}
