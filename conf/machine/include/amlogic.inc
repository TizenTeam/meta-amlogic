MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " ${@bb.utils.contains('MACHINE_FEATURES', 'fb', 'amlsetfb', '', d)}"

PREFERRED_PROVIDER_virtual/libgles1 = "opengl-amlogic"
PREFERRED_PROVIDER_virtual/libgles2 = "opengl-amlogic"
PREFERRED_PROVIDER_virtual/egl = "opengl-amlogic"

PREFERRED_PROVIDER_virtual/kernel = "linux-amlogic"
PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"
