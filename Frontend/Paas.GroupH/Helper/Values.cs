using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Paas.GroupH.Model;
using AndroidFile = Java.IO.File;

namespace Paas.GroupH.Helper
{
    public static class Values
    {
        public const string LogTag = "GroupHLogs";
        public static string FolderPath { get; set; }

        public const string Foldername = "grouph";

        public const string DefaultHost = "http://springboot-env.kvnpa7pnha.eu-central-1.elasticbeanstalk.com/";
        public const string DefaultPort = "80";
        public const string DefaultUploadPath = "/img";
        public const string DefaultProgressPath = "/getProgress/";
        public const string DefaultImagePath = "/getImage/";

        public static string Host { get; set; }
        public static string Port { get; set; }


        public static int TestIndex = -1;

        public static void CreateDir()
        {
            var directory = new AndroidFile(Android.OS.Environment.ExternalStorageDirectory, "grouph");

            Helper.Values.FolderPath = directory.Path;

            if (!directory.Exists())
                directory.Mkdirs();

            if (!System.IO.File.Exists(System.IO.Path.Combine(directory.Path, "config.json")))
            {
                var settings = new ConfigModel()
                {
                    BaseUrl = Helper.Values.DefaultHost,
                    Port = Helper.Values.DefaultPort,
                    Path = Helper.Values.FolderPath
                };

                var save = Newtonsoft.Json.JsonConvert.SerializeObject(settings);

                try
                {
                    using (StreamWriter writer = new StreamWriter(System.IO.Path.Combine(directory.Path, "config.json")))
                    {
                        writer.Write(save);
                    }
                }
                catch (Exception ex)
                {
                }
            }
            else
            {
                using (StreamReader reader = new StreamReader(System.IO.Path.Combine(directory.Path, "config.json")))
                {
                    var settings = Newtonsoft.Json.JsonConvert.DeserializeObject<ConfigModel>(reader.ReadToEnd());

                    Helper.Values.Host = settings.BaseUrl;
                    Helper.Values.Port = settings.Port.ToString();

                }
            }

        }

    }
}