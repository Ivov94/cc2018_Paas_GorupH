using Android;
using Android.Content;
using Android.Content.Res;
using Android.Graphics;

using Android.OS;
using Android.Provider;
using Android.Support.V4.App;
using Android.Support.V4.Content;
using Android.Views;
using Android.Widget;
using Paas.GroupH.Model;
using System;
using System.IO;
using AndroidFile = Java.IO.File;

namespace Paas.GroupH.Fragments
{
    public class Fragment3 : Fragment
    {

        private EditText hostEdit;
        private EditText portEdit;
        private Button saveButton;
        private TextView infoText;
        
        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            
        }

        public static Fragment3 NewInstance()
        {
            var frag3 = new Fragment3 { Arguments = new Bundle() };
            return frag3;
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            var ignored = base.OnCreateView(inflater, container, savedInstanceState);

            return inflater.Inflate(Resource.Layout.fragment3, null);
        }

        public override void OnViewCreated(View view, Bundle savedInstanceState)
        {
            base.OnViewCreated(view, savedInstanceState);

            hostEdit = view.FindViewById<EditText>(Resource.Id.hostText);
            portEdit = view.FindViewById<EditText>(Resource.Id.hostPort);
            infoText = view.FindViewById<TextView>(Resource.Id.infoText);
            AssetManager assets = Resources.Assets;

            var directory = new AndroidFile(Android.OS.Environment.ExternalStorageDirectory, "grouph");

            using (StreamReader reader = new StreamReader(System.IO.Path.Combine(directory.Path, "config.json")))
            {
                var settings = Newtonsoft.Json.JsonConvert.DeserializeObject<ConfigModel>(reader.ReadToEnd());

                hostEdit.Text = settings.BaseUrl;
                portEdit.Text = settings.Port.ToString();

            }

            saveButton = view.FindViewById<Button>(Resource.Id.btnSaveSettings);
            saveButton.Click += SaveSettings_Click;


        }

        private void SaveSettings_Click(object sender, System.EventArgs e)
        {
            var directory = new AndroidFile(Android.OS.Environment.ExternalStorageDirectory, "grouph");

            var settings = new ConfigModel()
            {
                BaseUrl = hostEdit.Text,
                Port = portEdit.Text
            };
            var save = Newtonsoft.Json.JsonConvert.SerializeObject(settings);

            try
            {

                using (StreamWriter writer = new StreamWriter(System.IO.Path.Combine(directory.Path, "config.json")))
                {
                    writer.Write(save);
                }
                infoText.Text = "saved...";
                infoText.Visibility = ViewStates.Visible;
            }
            catch (Exception ex)
            {
                infoText.Text = ex.Message;
                infoText.Visibility = ViewStates.Visible;
            }

        }
    }
}