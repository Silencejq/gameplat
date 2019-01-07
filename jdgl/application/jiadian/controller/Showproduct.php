<?php

namespace app\jiadian\controller;

use think\Controller;
use think\Request;
use app\jiadian\model\Product;
use think\facade\Cookie;
use app\jiadian\model\Department;

class Showproduct extends Controller{
    public function show(Request $request){
        $role= Cookie::get('role');
        $department= Cookie::get('department');
        if($role==1 or $role==4){
        $product = Product::all();
        $a=0;
        foreach ($product as $a1) {
            $b=array('id'=>"$a1->productid",'title'=>"$a1->productname",'category'=>"$a1->productsort");
            $q1[$a]=$b;
            $a=$a+1;
        }
        $q=array('code'=>0,'msg'=>"","count"=>1000,'data'=>$q1);
        $p=json_encode($q);
        echo $p;
    }
        else{
            $d1= Department::all(['departmentL'=>$department]);
            $a=0;
            foreach ($d1 as $result) {
                $departmentS[$a]=$result->departmentS;
                $a=$a+1;
            }
            $product = Product::all(function($query){
                $query->where('productsort', 'in', $departmentS);
            });
            $a=0;
            foreach ($product as $a1) {
                $b=array('id'=>"$a1->productid",'title'=>"$a1->productname",'category'=>"$a1->productsort");
                $q1[$a]=$b;
                $a=$a+1;
            }
            $q=array('code'=>0,'msg'=>"","count"=>1000,'data'=>$q1);
            $p=json_encode($q);
            echo $p;
        }
    }
    public function add(Request $request){
        $name = $request->param('cname');
        $sort = $request->param('title');
        $product = new Product;
        $maxid = Product::get(function($query){
            $query->order('productid','desc')->limit(1);
        });
        $id=100001+substr($maxid->productid,6,5);
        $id=substr($id,1,5);
        $t=time();
        $id='PD'.date("Y",$t).$id;
        $product->productid=$id;
        $product->productname=$name;
        $product->productsort=$sort;
        if($product->save()){echo '添加成功啦！';}
}
}