Easer [![Build Status](https://travis-ci.org/renyuneyun/Easer.svg?branch=master)](https://travis-ci.org/renyuneyun/Easer) [![weblate](https://hosted.weblate.org/widgets/easer/-/svg-badge.svg)](https://hosted.weblate.org/engage/easer/?utm_source=widget) [![codecov](https://codecov.io/gh/renyuneyun/Easer/branch/master/graph/badge.svg)](https://codecov.io/gh/renyuneyun/Easer) [ ![Download](https://api.bintray.com/packages/renyuneyun/Android/Easer/images/download.svg) ](https://bintray.com/renyuneyun/Android/Easer/_latestVersion)  
[![matrix-chat](https://matrix.to/img/matrix-badge.svg)](https://matrix.to/#/#Easer:matrix.org) [![Backers on Open Collective](https://opencollective.com/Easer/backers/badge.svg)](Contributor.md)
=======
[<img src="https://f-droid.org/badge/get-it-on.png"
      alt="Get it on F-Droid"
      height="60">](https://f-droid.org/app/ryey.easer)
<img align="right" src='./app/src/main/ic_launcher-web.png' width='128' height='128'/>

Ease your life by automatically performing routine actions.

**Notice**: Due to recent time constraints (since late 2020), Easer's maintainance speed will be affected. But since the main structure and hierarchy is stable, no drastic changes to Easer's *structure* need to be made (but extensions may require some changes of course), except for supporting remote Plugins of Events and Conditions (and supported federated inter-machine automation, as the long-term vision). Interested people can read the relevant sections below to learn how to add the functions you would like to see, or how to locate bugs. PRs will be read whenever there is time, and be merged and release in the usual way -- beta first, and then stable.

Introduction
-----
Make your smart phone smarter: tell it what to do under different situations. It can handle Events, Conditions, and combine events to new custom complex events.  

This document centers on development-related things of Easer.

If you are looking for the description of Easer's functionality, please refer to [the website](https://renyuneyun.github.io/Easer/en/).

Extending Easer
------

There are three main topics of adding functions to Easer: *mechanism*, *skills* (used to be called *local plugin)*, and *remote skill* (*plugin*).

*Mechanism* is the core part of Easer, and adding this requires a good understanding of Easer's code; optimization and documenting are also welcome.
For most cases, what needed is to add new *Event*, *Condition* and *Operation*, which is done by adding *skill* or *remote skill* (*plugin*).

### Skill

Adding a *skill* needs to add your *Event*, *Condition* or *Operation* to the `skills` package of Easer's code. There are a few scripts to simplify the process, and existing skills can act as examples.

Details are described in [this document](https://renyuneyun.github.io/Easer/en/EXTEND).

### Remote Skill (Plugin)

*Remote Skill* (called *Remote Plugin* previously) is introduced in v0.7, aiming at using standalone applications to extend Easer's functionality.
Each *remote skill* is a standalone app; it is in theory also possible to include multiple *remote skills* in one app. Note this feature is rather new and the interface might not be stable yet (though it's unlikely the interface will change much).

Currently, only *Operation* can be extended as *remote skills*. [The *diagram* directory](diagram/) contains some useful information, e.g. [a sequence diagram](diagram/communication.png). [This repo](https://github.com/renyuneyun/EaserOperationPluginExample) is an example implementation of *remote operation plugin*, as well as documents. Better documentations are also welcome.


Support Easer
------
### Translation
Easer's translation work is hosted on [Hosted Weblate](https://hosted.weblate.org/projects/easer/), the official hosted instance of [weblate](https://weblate.org), an FLOSS platform for crowd-sourced web translation.

We kindly welcome anyone to contribute any amount of translation.
Translations will be licensed under [CC-0](https://creativecommons.org/choose/zero/) by default, which effectively means they will enter [Public Domain](https://en.wikipedia.org/wiki/Public_domain); please contact us in advance if you would like a different license.

### Raising issues, commenting on issues and solving issues
If you encounter problems when using Easer, you can submit an issue. The more detail you can provide, the better -- it will let the issue be pinned down faster.
You can also open an issue if you think there are features that Easer should have.

You are also welcome to comment on existing issues. If you believe you have the same problem (or idea), you can provide more information about it. Discussion is always welcome.
Issue expecting ideas are labeled with [RFC](https://github.com/renyuneyun/Easer/issues?q=is%3Aopen+label%3A%22RFC+%2F+Discussion+Wanted%22).

Want to do more but don't know where to start? See issues labeled with GFC (Good For Contributors) [L0](https://github.com/renyuneyun/Easer/issues?q=is%3Aissue+is%3Aopen+label%3A%22GFC%3A+L0%22), [L1](https://github.com/renyuneyun/Easer/issues?q=is%3Aissue+is%3Aopen+label%3A%22GFC%3A+L1%22), [L2](https://github.com/renyuneyun/Easer/issues?q=is%3Aissue+is%3Aopen+label%3A%22GFC%3A+L2%22). These issues usually have clear target and involve few components; the L0, L1 and L2 are my subjective classification of the level of difficulty (ascending).

If you are a developer, you may possess the knowledge and time to solve some issues. You can fork the repo, solve the problem, and create a pull request. Then, your code can be merged, and you can be appreciated by others (and you will be listed in the *Contributors* list unless you don't like to).
You're also welcome to create pull requests for issues not raised by others, but first, please create an issue describing what you want to do (and that you are going to do it).

### Test coverage
Test is important.
Easer is far from being tested too much -- in fact, most of its code has not been automatically tested.

Contribution to test is very much appreciated, especially to the core (including the UI, data backend and the services).

### Donation

If you would like to make a donation, please see [DONATE](https://renyuneyun.github.io/Easer/en/DONATE).

Any amount of help is appreciated.

Acknowledgement
------

### Contributors and Backers

The development of Easer receives the support and donation from the community. Thank you guys.

The [Contributor](Contributor.md) document lists this in more detail.

### Third-party

* [Logger](https://github.com/orhanobut/logger): Apache License v2
* [android-flowlayout](https://github.com/ApmeM/android-flowlayout): Apache License v2
* [Guava](https://github.com/google/guava): Apache License v2
* [StickyListHeaders](https://github.com/emilsjolander/StickyListHeaders): Apache License v2
* [AppIntro](https://github.com/AppIntro/AppIntro): Apache License v2
* [GraphView](https://github.com/Team-Blox/GraphView): Apache License v2
* [locale-helper-android](https://github.com/zeugma-solutions/locale-helper-android): Apache License v2
* [material-about-library](https://github.com/daniel-stoneuk/material-about-library): Apache License v2

* Drawable files named as `*-fa-*` all come from [fontawesome](https://fontawesome.com/): CC-BY 4.0

Licensing
------
Copyright (c) 2016 - 2021 Rui Zhao (renyuneyun) <renyuneyun@gmail.com>

Unless otherwise stated, the program is licensed under GPLv3+ (See LICENSE)

Tools under `utils/` directory are licensed under Apache 2.0 (See `utils/LICENSE`)

### Why GPL?

The expected functions of Easer require access to personal information (e.g. location, calendar) and networking capabilities. We would never want a tool that is expected to better facilitate our lives to spy on us, so we must prevent that from happening as best as we can. The only way to do this is to allow anyone to inspect every part of Easer, which is to say that Easer (and any derived works) must be made open source.
Because of the design of Easer, functionality will eventually become modules / plugins. The GPL requires that derived works also be licensed under the GPL, and thus prevents malicious code from sneaking into these parts.

In fact, ensuring that derived works / plugins are licensed under the **GPL** is unnecessary -- they only need to be **open source**. However, GPL is the only license (that I know of) which requires that derived works / plugins are open sourced, so it's the only choice.
