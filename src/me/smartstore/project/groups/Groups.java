package me.smartstore.project.groups;

public class Groups {
	private Group[] groups;
	private static Groups allGroups;
	private static final int DEFAULT_SIZE = GroupType.values().length;
	private int count = 0; // GroupType과 Parameter까지 들어올 때 증가


	public static Groups getInstance() {
		if (allGroups == null) {
			allGroups = new Groups();
		}
		return allGroups;
	}

	private Groups() {
		groups = new Group[DEFAULT_SIZE];

		int i = 0;
		for (GroupType groupType: GroupType.values()) {
			groups[i] = new Group(groupType, null);
			i++;
		}
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public void add(Group group) {
		if (group == null) return;

		if (count < DEFAULT_SIZE) {
			groups[count] = group;
			count++;
		}
	}

	public Group get(int i) {
		if (!(i >= 0 && i < count)) return null;
		return groups[i];
	}

	public boolean isGroupType(GroupType groupType){
		for (int i = 0; i < groups.length; i++) {
			if(allGroups.get(i).getGroupType() == groupType)
				return true;
		}
		return false;
	}

	public Group get(GroupType groupType){
		for (Group group : groups) {
			if (group.getGroupType() == groupType)
				return group;
		}
			return null;
	}
	public void set(int i, Group group) {
		if (i < 0 || i >= groups.length) return;
		groups[i] = group;
	}
	
	public Group pop(int i) {
		if (!(i >= 0 && i < groups.length)) return null;
		if (isEmpty()) return null;
		if (groups[i] == null) return null;

		Group popNode = groups[i];
		groups[i] = null;

		return popNode;
		
	}
	
	public Group pop() {
		return pop(count - 1);
	}

	public void print() {
		for (int i = 0; i < count; i++) {
			if (groups[i] != null) {
				System.out.println(groups[i]);
			}
		}
	}

	public int getCount() {
		return groups.length;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Group group : groups) {
			str.append(group).append("\n");
		}
		return str.toString();
	}

}
