This README file contains information on building the meta-amlogic
BSP layer, and booting the images contained in the /binary directory.
Please see the corresponding sections below for details.


Dependencies
============

This layer depends on:

    URI: git://git.yoctoproject.org/poky
    layers: poky
    branch: fido

Optional:

    URI: git@github.com:openembedded/meta-openembedded.git
    layers: meta-openembedded
    branch: fido

How to use it:

1. source poky/oe-init-build-env build/wetekplay
2. Add needed layer to bblayers.conf:
    - meta-amlogic
    - (meta-openembedded)
3. Set MACHINE to "wetekplay"/"odroidc1" in local.conf
4. bitbake core-image-base
5. dd to a SD card the generated sdimg file
6. Boot your Device.

Contributing
============

The linux-meson mailinglist (linux-meson@googlegroups.com) is used for questions, comments and patch review. It is subscriber only, so please register before posting.

Please use github for pull requests: [https://github.com/linux-meson/meta-amlogic/pulls](https://github.com/linux-meson/meta-amlogic/pulls)

Reporting bugs
==============

The github issue tracker ([https://github.com/linux-meson/meta-amlogic/issues](https://github.com/linux-meson/meta-amlogic/issues)) is being used to keep track of bugs, but it recommended ask on the mailinglist (linux-meson@googlegroups.com) first.

Maintainer: Christian Ege [k4230r6@gmail.com](mailto:k4230r6@gmail.com)

Table of Contents
=================

1. Activating the Framebuffer
2. Config tweaks
3. Booting the images in /binary


1. Activating the Framebuffer
========================================

To enable the Framebuffer the layer meta-openembedded is required. This is due to the fact
that this layer contains the framebuffer tools.

Add *fb* to MACHINE_FEATURES in **conf/local.conf**.

    MACHINE_FEATURES_prepend = "fb"

2. Config Tweaks
================

It is recommended to add/change the following config options to **conf/local.conf**

Enable ipk/opk Pacakage Managment. This is a lightweight package Manager

    PACKAGE_CLASSES ?= "package_ipk"

Enable SSH Server and add Package Managment to the image

    EXTRA_IMAGE_FEATURES = "debug-tweaks package-management ssh-server-dropbear"

3. Booting the images in /binary
=================================

This BSP contains bootable live images, which can be used to directly
boot Yocto off of a SD-Card drive.

Under Linux, insert a SD-Card drive.  Assuming the SD-Card drive
takes device /dev/sdf, use dd to copy the live image to it.  For
example:

    # dd if=core-image-base-wetekplay-20101207053738.hddimg of=/dev/sdf
    # sync
    # eject /dev/sdf

This should give you a bootable SD-Card device.  Insert the device
into a bootable USB socket on the target, and power on.  This should
result in a system booted to the Sato graphical desktop.

If you want a terminal, use the arrows at the top of the UI to move to
different pages of available applications, one of which is named
'Terminal'.  Clicking that should give you a root terminal.

If you want to ssh into the system, you can use the root terminal to
ifconfig the IP address and use that to ssh in.  The root password is
empty, so to log in type 'root' for the user name and hit 'Enter' at
the Password prompt: and you should be in.
