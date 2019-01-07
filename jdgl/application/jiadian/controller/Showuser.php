<?php

namespace app\jiadian\controller;

use think\Controller;
use think\Request;
use app\jiadian\model\Client;

class Showuser extends Controller
{
    public function show(Request $request){
        $client = Client::all();
        $a=0;
        foreach ($client as $a1) {
            $b=array('id'=>"$a1->clientid",'username'=>"$a1->clientname",'city'=>"$a1->home");
            $q1[$a]=$b;
            $a=$a+1;
        }
        $q=array('code'=>0,'msg'=>"","count"=>1000,'data'=>$q1);
        $p=json_encode($q);
        echo $p;
        
    }
    public function add(Request $request){
        $client = new Client;
        $id = $request->param('id');
        $name = $request->param('name');
        $home = $request->param('home');
        $vip = $request->param('vip');
        if($vip=='1'){$vip='是';}else{$vip='否';}
        
        if($id ==''){$maxid = Client::get(function($query){
          $query->order('clientid','desc')->limit(1);
        });
        // $id=sprintf('%05s',(substr($maxid->clientid,4,5)+1));
        // $id='CT02'.$id;
        $id=100001+substr($maxid->clientid,4,5);
        $id=substr($id,1,5);
        $id='CT02'.$id;
        $client->clientid=$id;
        $client->clientname=$name;
        $client->home=$home;
        $client->vip=$vip;
        if($client->save()){echo '添加成功啦！';}}
        
        else if($name==''){
            $client = Client::get(['clientid'=>$id]);
            if($client){$client->delete();echo '删除成功啦！';}
            else{echo '没有该用户存在';}
        }
        
        else{
            $client = Client::get(['clientid'=>$id]);
            $client->clientname=$name;
            $client->home=$home;
            $client->vip=$vip;
            if($client->save()){echo '修改成功啦！';}
            else{echo '没有该用户存在';}
        }
    }
    public function update(Request $request){
        $id = $request->param('id');
        $name = $request->param('name');
        $home = $request->param('home');
        $vip = $request->param('vip');
        if($vip=='1'){$vip='是';}else{$vip='否';}
        $client = Client::get(['clientid'=>$id]);
        $client->clientname=$name;
        $client->home=$home;
        $client->vip=$vip;
        if($client->save()){echo '修改成功啦！';}
    }
    public function search(Request $request){
        $id = $request->param('id');
        $a1 = Client::get(['clientid'=>$id]);
        $data=array('id'=>"$id",'username'=>"$a1->clientname",'city'=>"$a1->home");
        $q=array('code'=>0,'msg'=>"","count"=>1000,'data'=>$data);
        $p=json_encode($q);
        echo $p;
    }
}