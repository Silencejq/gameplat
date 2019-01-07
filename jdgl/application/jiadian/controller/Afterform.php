<?php

namespace app\jiadian\controller;

use think\Controller;
use think\Request;
use app\jiadian\model\Aftersale;

class Afterform extends Controller{
    public function show(Request $request){
        $form = Aftersale::all();
        $a=0;
        foreach ($form as $a1) {
            $b=array('id'=>"$a1->afromid",'title'=>"$a1->question",'date'=>"$a1->afterdate",
            'category'=>"$a1->quetionsort",'zt'=>"$a1->status");
            $q1[$a]=$b;
            $a=$a+1;
        }
        $q=array('code'=>0,'msg'=>"","count"=>1000,'data'=>$q1);
        $p=json_encode($q);
        echo $p;
    }
    public function add(Request $request){
        $sort= $request->param('sort');
        $question = $request->param('question');
        $sale= new Aftersale;
        $maxid = Aftersale::get(function($query){
            $query->order('afromid','desc')->limit(1);
        });
        $id=100001+substr($maxid->afromid,8,5);
        $id=substr($id,1,5);
        $t=time();
        $id='SV'.date("Ym",$t).$id;
        $sale->afromid=$id;
        $sale->quetionsort=$sort;
        $sale->question=$question;
        if($sale->save()){echo '添加成功啦！';}
    }
}