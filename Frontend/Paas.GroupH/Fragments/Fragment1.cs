using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Support.V4.App;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Bumptech.Glide;
using Java.IO;
using Paas.GroupH.Model;
using System.Collections.Generic;
using System.Linq;

namespace Paas.GroupH.Fragments
{
    public class Fragment1 : Fragment
    {
        private ImageView imageView;
        private int Index = 0;
        private Button prevImage;
        private Button nextImage;
        private Button uploadImage;

        //private List<ImageModel> Images
        //{
        //    get
        //    {
        //        return GetImagesUri();
        //    }
        //}

        //private List<ImageModel> GetImagesUri()
        //{
        //    var files = System.IO.Directory.GetFiles(Helper.Values.FolderPath);

        //    if (!files.Any())
        //    {
        //        return new List<ImageModel>();
        //    }

        //    foreach (var file in files)
        //    {
        //        if (!Images.Any(x => x.Filename == file))
        //        {
        //            Images.Add(new ImageModel()
        //            {
        //                Filename = file,
        //                Fullpath = System.IO.Path.Combine(Helper.Values.FolderPath, file)
        //            });

        //        }
        //    }

        //    return Images;
        //}


        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your fragment here
        }

        public static Fragment1 NewInstance()
        {
            var frag1 = new Fragment1 { Arguments = new Bundle() };
            return frag1;
        }


        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            var ignored = base.OnCreateView(inflater, container, savedInstanceState);

            return inflater.Inflate(Resource.Layout.fragment1, null);
        }
        public override void OnViewCreated(View view, Bundle savedInstanceState)
        {
            base.OnViewCreated(view, savedInstanceState);

            imageView = view.FindViewById<ImageView>(Resource.Id.imageView);

            var files = System.IO.Directory.GetFiles(Helper.Values.FolderPath, "*.jpg");
            Log.Info(Helper.Values.LogTag, "file count: " + files.Length);
            Log.Info(Helper.Values.LogTag, "first file: " + files.FirstOrDefault());
            Log.Info(Helper.Values.LogTag, "first file. " + System.IO.Path.Combine(Helper.Values.FolderPath, files.FirstOrDefault()));
            
            prevImage = view.FindViewById<Button>(Resource.Id.prevImage);
            nextImage = view.FindViewById<Button>(Resource.Id.nextImage);
            uploadImage = view.FindViewById<Button>(Resource.Id.sendImage);

            if(files != null)
            {
                int height = Resources.DisplayMetrics.HeightPixels;
                int width = imageView.Width;

                var resizedBitmap = Helper.ImageEditor.LoadAndResizeBitmap(files.LastOrDefault(), width, height);
                imageView.SetImageBitmap(resizedBitmap);
            }

            //string[] title = new string[Images.Count];
            //for(int i = 0; i < Images.Count; i++)
            //{
            //    title[i] = Images[i].Filename;
            //}
            //Log.Info(Helper.Values.LogTag, "folderpath: " + Helper.Values.FolderPath);
            


        }
    }
}