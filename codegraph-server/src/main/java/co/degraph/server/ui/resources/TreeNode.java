/**
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package co.degraph.server.ui.resources;

import java.util.Objects;

public class TreeNode implements Comparable<TreeNode> {

    private String id;
    private String name;
    private String type;
    private String parent;

    public TreeNode(String parent, String name, String type) {
        this.id = parent == null ? name : parent + "." + name;
        this.name = name;
        this.type = type;
        this.parent = parent;
    }

    @Override
    public int compareTo(TreeNode o) {
        return id.compareTo(o.id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return Objects.equals(id, treeNode.id) &&
            Objects.equals(name, treeNode.name) &&
            Objects.equals(type, treeNode.type) &&
            Objects.equals(parent, treeNode.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, parent);
    }

    @Override
    public String toString() {
        return "TreeNode{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", parent='" + parent + '\'' +
            '}';
    }
}
