/**
 * Copyright 2012 Twitter, Inc
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package cc.linkedme.commons.utils.useragent;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Operating System parsed data class
 *
 * @author Steve Jiang (@sjiang) <gh at iamsteve com>
 */
public class OS {
    public final String family, major, minor, patch, patchMinor;

    public OS(String family, String major, String minor, String patch, String patchMinor) {
        this.family = family;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.patchMinor = patchMinor;
    }

    public static OS fromMap(Map<String, String> m) {
        return new OS(m.get("family"), m.get("major"), m.get("minor"), m.get("patch"), m.get("patch_minor"));
    }

    public static String getOsVersion(String family, String major, String minor, String patch) {
        if (family.equals("iOS")) {
            if (StringUtils.isNotEmpty(major) && StringUtils.isNotEmpty(minor)) {
                return major + "_" + minor;
            }
            if (StringUtils.isNotEmpty(major) && StringUtils.isNotEmpty(minor) && StringUtils.isNotEmpty(patch)) {
                return major + "_" + minor + "_" + patch;
            }
        }
        if (family.equals("Android")) {
            if (StringUtils.isNotEmpty(major) && StringUtils.isNotEmpty(minor)) {
                return major + "." + minor;
            }
            if (StringUtils.isNotEmpty(major) && StringUtils.isNotEmpty(minor) && StringUtils.isNotEmpty(patch)) {
                return major + "." + minor + "." + patch;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof OS)) return false;

        OS o = (OS) other;
        return ((this.family != null && this.family.equals(o.family)) || this.family == o.family)
                && ((this.major != null && this.major.equals(o.major)) || this.major == o.major)
                && ((this.minor != null && this.minor.equals(o.minor)) || this.minor == o.minor)
                && ((this.patch != null && this.patch.equals(o.patch)) || this.patch == o.patch)
                && ((this.patchMinor != null && this.patchMinor.equals(o.patchMinor)) || this.patchMinor == o.patchMinor);
    }

    @Override
    public int hashCode() {
        int h = family == null ? 0 : family.hashCode();
        h += major == null ? 0 : major.hashCode();
        h += minor == null ? 0 : minor.hashCode();
        h += patch == null ? 0 : patch.hashCode();
        h += patchMinor == null ? 0 : patchMinor.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return String.format("{\"family\": %s, \"major\": %s, \"minor\": %s, \"patch\": %s, \"patch_minor\": %s}",
                family == null ? Constants.EMPTY_STRING : '"' + family + '"', major == null ? Constants.EMPTY_STRING : '"' + major + '"',
                minor == null ? Constants.EMPTY_STRING : '"' + minor + '"', patch == null ? Constants.EMPTY_STRING : '"' + patch + '"',
                patchMinor == null ? Constants.EMPTY_STRING : '"' + patchMinor + '"');
    }
}
