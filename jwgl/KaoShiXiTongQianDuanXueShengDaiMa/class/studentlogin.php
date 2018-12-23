<?php 
$servername = "localhost";
$username = "root";
$password = "123";
$dbname = "guanli";
 // 创建连接
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
$pname = isset($_POST["username"]) ? $_POST["username"] : "";
$ppwd = isset($_POST["passwd"]) ? $_POST["passwd"] : "";
if($pname==""){
    echo '干啥呢老弟？';
    exit;
}


// class rrr{public $message = 'fault';}
// $rrrr = new rrr();
// $rrrrr=json_encode($rrrr);
// echo $rrrrr;
// exit;


$p = array('message'=>'fault');
// $p['message']="fault";
if($pname=='777' or $ppwd=='555'){$q= json_encode($p);echo $q;exit;}
$sql='select * from think_user where user_name ="'.$pname.'"';
$result = mysqli_query($conn,$sql);
while ( $row = $result->fetch_array() ) {
    $realpwd=$row[1];
    $user_id=$row[7];
 }
//  $p["message"]=$user_id;$q= json_encode($p);echo $q;exit;
if($realpwd!=$ppwd){$q= json_encode($p);echo $q;exit;}
$p['message']="Success";
$p["sid"]=$pname;
$sql='select * from think_student where student_id ="'.$user_id.'"';
$result2=mysqli_query($conn,$sql);
while ( $row = $result2->fetch_array() ) {
    $p["cid"]=$row['class'];
    $p["name"]=$row[1];
 }
$q= json_encode($p);
echo $q;
exit;
?>