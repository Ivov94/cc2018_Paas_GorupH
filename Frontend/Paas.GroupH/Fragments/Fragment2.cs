using Android;
using Android.Content;
using Android.Graphics;

using Android.OS;
using Android.Provider;
using Android.Support.V4.App;
using Android.Support.V4.Content;
using Android.Views;
using Android.Widget;
using System.Reflection;
using System.Threading.Tasks;
using AndroidFile = Java.IO.File;


namespace Paas.GroupH.Fragments
{
    public class Fragment2 : Fragment
    {
        private ImageView _imgView;
        private Bitmap _resizedBitmap;
        private AndroidFile _file;
        private AndroidFile _directory;

        private Button btnCamera;
        private Button btnPostImage;
        private TextView infoPost;

        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            
        }

        public static Fragment2 NewInstance()
        {
            var frag2 = new Fragment2 { Arguments = new Bundle() };
            return frag2;
        }

        private string UniqueImgFileName() => $"groupH_{System.DateTime.Now.Ticks.ToString()}.jpg";
        private string _providerName = $"grouph";

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            var ignored = base.OnCreateView(inflater, container, savedInstanceState);

            return inflater.Inflate(Resource.Layout.fragment2, null);
        }

        public override void OnViewCreated(View view, Bundle savedInstanceState)
        {
            base.OnViewCreated(view, savedInstanceState);
            btnCamera = view.FindViewById<Button>(Resource.Id.btntakePicture);
            btnPostImage = view.FindViewById<Button>(Resource.Id.btnPostPicture);
            infoPost = view.FindViewById<TextView>(Resource.Id.infoPost);

            btnCamera.Click += TakeAPicture_Click;

            _imgView = view.FindViewById<ImageView>(Resource.Id.imgView);

            btnPostImage.Click += PostRestService_Click;

        }

        private void TakeAPicture_Click(object sender, System.EventArgs e)
        {
            Intent intent = new Intent(MediaStore.ActionImageCapture);
            _directory = new AndroidFile(Environment.ExternalStorageDirectory, "grouph");
            if (!_directory.Exists())
            {
                _directory.Mkdirs();
            }

            _file = new AndroidFile(_directory, UniqueImgFileName());

            intent.PutExtra(MediaStore.ExtraOutput, FileProvider.GetUriForFile(this.Context, _providerName, _file));
            intent.SetFlags(ActivityFlags.GrantWriteUriPermission);
            StartActivityForResult(intent, 0);
        }

        private void PostRestService_Click(object sender, System.EventArgs e)
        {
            var uri = Android.Net.Uri.FromFile(_file);

            var input = Activity.ContentResolver.OpenInputStream(uri);

            infoPost.Text = "uploading...";
            infoPost.Visibility = ViewStates.Visible;

            var image = RestService.PostData(input, _file.Name);
            
            


        }

        public override void OnActivityResult(int requestCode, int resultCode, Intent data)
        {
            base.OnActivityResult(requestCode, (int)resultCode, data);
            
            int height = Resources.DisplayMetrics.HeightPixels;
            int width = _imgView.Width;

            _resizedBitmap = Helper.ImageEditor.LoadAndResizeBitmap(_file.Path, width, height);
            _imgView.SetImageBitmap(_resizedBitmap);

            btnCamera.Visibility = ViewStates.Invisible;
            btnPostImage.Visibility = ViewStates.Visible;

        }

        
    }
}