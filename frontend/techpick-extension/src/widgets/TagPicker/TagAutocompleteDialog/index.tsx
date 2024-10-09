import { useEffect, useRef, useState } from 'react';
import { Command } from 'cmdk';
import {
  SelectedTagItem,
  SelectedTagListLayout,
  useTagStore,
  tagTypes,
} from '@/entities/tag';
import { DeleteTagDialog, DeselectTagButton } from '@/features/tag';
import { TagInfoEditPopoverButton } from '@/widgets/TagPicker/TagInfoEditPopoverButton';
import {
  filterCommandItems,
  CREATABLE_TAG_KEYWORD,
} from './TagAutocompleteDialog.lib';
import {
  tagDialogPortalLayout,
  commandInputStyle,
  tagListItemStyle,
  tagListItemContentStyle,
  tagCreateTextStyle,
  tagListStyle,
} from './TagAutocompleteDialog.css';

export function TagAutocompleteDialog({
  open,
  onOpenChange,
  container,
}: TagSelectionDialogProps) {
  const [tagInputValue, setTagInputValue] = useState('');
  const [canCreateTag, setCanCreateTag] = useState(false);
  const tagInputRef = useRef<HTMLInputElement | null>(null);
  const {
    tagList,
    selectedTagList,
    fetchingTagState,
    selectTag,
    fetchingTagList,
    createTag,
  } = useTagStore();

  const focusTagInput = () => {
    tagInputRef.current?.focus();
  };

  const clearTagInputValue = () => {
    setTagInputValue('');
  };

  const onSelectTag = (tag: tagTypes.TagType) => {
    selectTag(tag);
    focusTagInput();
    clearTagInputValue();
  };

  useEffect(
    function fetchTagList() {
      fetchingTagList();
    },
    [fetchingTagList]
  );

  useEffect(
    function checkIsCreatableTag() {
      const isUnique = !tagList.some((tag) => tag.name === tagInputValue);
      const isNotInitialValue = tagInputValue.trim() !== '';
      const isCreatable = isUnique && isNotInitialValue;

      setCanCreateTag(isCreatable);
    },
    [tagInputValue, tagList]
  );

  if (fetchingTagState.isPending) {
    return <h1>Loading...</h1>;
  }

  return (
    <Command.Dialog
      open={open}
      onOpenChange={onOpenChange}
      container={container?.current ?? undefined}
      className={tagDialogPortalLayout}
      filter={filterCommandItems}
    >
      {/**선택한 태그 리스트 */}
      <SelectedTagListLayout focusStyle="focus">
        {selectedTagList.map((tag) => (
          <SelectedTagItem key={tag.id} tag={tag}>
            <DeselectTagButton tag={tag} onClick={focusTagInput} />
          </SelectedTagItem>
        ))}

        <Command.Input
          className={commandInputStyle}
          ref={tagInputRef}
          value={tagInputValue}
          onValueChange={setTagInputValue}
        />
      </SelectedTagListLayout>

      {/**전체 태그 리스트 */}
      <Command.List className={tagListStyle}>
        <Command.Empty>No results found.</Command.Empty>

        {tagList.map((tag) => (
          <Command.Item
            key={tag.id}
            className={tagListItemStyle}
            onSelect={() => onSelectTag(tag)}
            keywords={[tag.name]}
          >
            <span className={tagListItemContentStyle}>{tag.name}</span>
            <TagInfoEditPopoverButton tag={tag} />
          </Command.Item>
        ))}

        {canCreateTag && (
          <Command.Item
            className={tagListItemStyle}
            value={tagInputValue}
            keywords={[CREATABLE_TAG_KEYWORD]}
            onSelect={async () => {
              const newTag = await createTag(tagInputValue);

              if (!newTag) {
                // Todo: error handling
                // newTag or postTagState을 이용할 예정.
                return;
              }

              onSelectTag(newTag);
            }}
            disabled={!canCreateTag}
          >
            <span className={tagListItemContentStyle}>{tagInputValue}</span>
            <span className={tagCreateTextStyle}>생성</span>
          </Command.Item>
        )}
      </Command.List>

      {/**DeleteTagDialog를 닫고도 Command.Dialog가 켜져있기위해서 Command.Dialog 내부에 있어야합니다.*/}
      <DeleteTagDialog />
    </Command.Dialog>
  );
}

interface TagSelectionDialogProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  container?: React.RefObject<HTMLElement>;
}
